package factories;


import data.schema.TopicTupleSerializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import settings.ProjectSettings;

import static settings.ProjectSettings.DEFAULT_RESULT_SINK_TOPIC;
import static settings.ProjectSettings.DEFAULT_RESULT_SINK_TOPIC_NAME;


class KafkaSinkStream {
    static <T> SinkFunction getKafkaSink(ParameterTool parameterTool, String defaultTopic, SerializationSchema<T> sink) {
        final String topic;

        return new FlinkKafkaProducer<T>(
                defaultTopic,
                sink,
                parameterTool.getProperties()
        );
    }


    static <K> SinkFunction getTuplesKafkaSink(ParameterTool parameterTool, SerializationSchema<K> valueSchema) {
        return new FlinkKafkaProducer<>(
                parameterTool.get(DEFAULT_RESULT_SINK_TOPIC_NAME, DEFAULT_RESULT_SINK_TOPIC),
                new TopicTupleSerializationSchema<>(valueSchema),
                parameterTool.getProperties()
        );


    }


}
