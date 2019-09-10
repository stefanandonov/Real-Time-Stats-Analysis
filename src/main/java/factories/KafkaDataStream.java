package factories;


import data.control.UserRulesControlMessage;
import data.messages.InputData;
import data.schema.InputDataSchema;
import data.schema.UserRulesControlMessageSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import settings.ProjectSettings;

import java.util.regex.Pattern;

public class KafkaDataStream {
    static DataStream<InputData> getDataStream(ParameterTool parameterTool,
                                               StreamExecutionEnvironment env) {
        String regexTopic = parameterTool.get(
                ProjectSettings.DEFAULT_MESSAGE_TOPIC_NAME,
                ProjectSettings.DEFAULT_MESSAGE_TOPIC
        );

        Pattern pattern = Pattern.compile(regexTopic);

        FlinkKafkaConsumer<InputData> consumerData = new FlinkKafkaConsumer<>(
                pattern,
                new InputDataSchema(),
                parameterTool.getProperties()
        );

        int parallelism = parameterTool.getInt(
                ProjectSettings.DEFAULT_KAFKA_DATA_PARALLELISM_NAME,
                ProjectSettings.DEFAULT_KAFKA_DATA_PARALLELISM
        );

        return env.addSource(consumerData).setParallelism(parallelism);
    }


    static DataStream<UserRulesControlMessage> getControlStream(ParameterTool parameterTool, StreamExecutionEnvironment env) {
        String regexTopic = parameterTool.get(
                ProjectSettings.DEFAULT_CONTROL_TOPIC_NAME,
                ProjectSettings.DEFAULT_CONTROL_TOPIC
        );

        Pattern pattern = Pattern.compile(regexTopic);

        FlinkKafkaConsumer<UserRulesControlMessage> consumerControl = new FlinkKafkaConsumer<>(
                pattern,
                new UserRulesControlMessageSchema(),
                parameterTool.getProperties());

        int parallelism = parameterTool.getInt(
                ProjectSettings.DEFAULT_KAFKA_CONTROL_PARALLELISM_NAME,
                ProjectSettings.DEFAULT_KAFKA_CONTROL_PARALLELISM
        );

        return env.addSource(consumerControl).setParallelism(parallelism);
    }

    public static void main(String[] args) {
        String regexTopic = "^sensor_topic";
        System.out.println(Pattern.compile(regexTopic));
    }
}