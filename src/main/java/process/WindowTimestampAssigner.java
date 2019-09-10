package process;

import data.messages.RuleStatisticsMessage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class WindowTimestampAssigner extends ProcessWindowFunction<Tuple2<RuleStatisticsMessage, String>, Tuple2<RuleStatisticsMessage, String>, String, TimeWindow> {
    @Override
    public void process(String s, Context context, Iterable<Tuple2<RuleStatisticsMessage, String>> iterable, Collector<Tuple2<RuleStatisticsMessage, String>> collector) throws Exception {
        for (Tuple2<RuleStatisticsMessage, String> ruleStatisticsMessageStringTuple2 : iterable) {
            ruleStatisticsMessageStringTuple2.f0.setAlTimestamp(context.window().getEnd());
            collector.collect(ruleStatisticsMessageStringTuple2);
        }
    }
}
