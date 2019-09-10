import data.TimestampsAndWatermarksAssigner;
import data.control.UserRulesControlMessage;
import data.messages.InputData;
import data.messages.MatchedMessage;
import data.messages.MatchedMessageKeySelector;
import data.messages.RuleStatisticsMessage;
import data.schema.RuleStatisticsMessageSchema;
import factories.SinkFactory;
import factories.SourceControlStreamFactory;
import factories.SourceDataStreamFactory;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.Collector;
import process.MatchingProcess;
import process.WindowTimestampAssigner;
import process.aggregations.RuleAggregationFunction;
import process.state.StateDescriptors;
import process.windowing.GenericWindowAssigner;
import settings.ProjectSettings;

public class UserRulesBasedAnalysis {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ParameterTool parameterTool = ParameterTool.fromArgs(args);

        env.setParallelism(parameterTool.getInt(ProjectSettings.DEFAULT_PROCESS_PARALLELISM_NAME, ProjectSettings.DEFAULT_PROCESS_PARALLELISM));

        DataStream<UserRulesControlMessage> controlStream = SourceControlStreamFactory.getStream(parameterTool, env);
        DataStream<InputData> dataStream = SourceDataStreamFactory.getStream(parameterTool, env).flatMap(new FlatMapFunction<InputData, InputData>() {
            @Override
            public void flatMap(InputData inputData, Collector<InputData> collector) throws Exception {
                if (inputData != null)
                    collector.collect(inputData);
            }
        });

        boolean eventTime = parameterTool.getBoolean(ProjectSettings.EVENT_TIME_PARAMETER, ProjectSettings.DEFAULT_EVENT_TIME);
        if (eventTime) {
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
            dataStream = TimestampsAndWatermarksAssigner.assignProtocolMessagesTimestamps(parameterTool, dataStream);
            controlStream = TimestampsAndWatermarksAssigner.assignControlMessagesTimestamps(controlStream);
        }

        BroadcastStream<UserRulesControlMessage> broadcastStream =
                controlStream.broadcast(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR);

        DataStream<MatchedMessage> matchedMessages = dataStream.connect(broadcastStream).process(new MatchingProcess());

//        long windowSize = parameterTool.getLong(ProjectSettings.STATS_WINDOW_SIZE_NAME, ProjectSettings.DEFAULT_STATS_WINDOW_SIZE);

//        matchedMessages.keyBy(new MatchedMessageKeySelector())
//                .window(TumblingEventTimeWindows.of(Time.milliseconds(windowSize)))
//                .aggregate(new RuleAggregationFunction(windowSize))
//                .print();

        DataStream<Tuple2<RuleStatisticsMessage, String>> statisticsStream = matchedMessages.keyBy(new MatchedMessageKeySelector())
                .window(new GenericWindowAssigner<>(eventTime))
                .aggregate(new RuleAggregationFunction(), new WindowTimestampAssigner());

        SinkFunction<Tuple2<RuleStatisticsMessage, String>> statisticsSink = SinkFactory.getTuplesSink(parameterTool, new RuleStatisticsMessageSchema());


        statisticsStream.addSink(statisticsSink);


        env.execute("real time data analysys");


    }
}
