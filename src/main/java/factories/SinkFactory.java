package factories;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import settings.ProjectSettings;

public class SinkFactory {


    public static <K> SinkFunction getTuplesSink(ParameterTool parameterTool, SerializationSchema<K> valueSchema) {
        boolean testMode = parameterTool.getBoolean(
                ProjectSettings.TEST_MODE,
                ProjectSettings.DEFAULT_TEST_MODE
        );

        if (testMode) {
            return new PrintSinkFunction<>();
        }
        return KafkaSinkStream.getTuplesKafkaSink(parameterTool, valueSchema);
    }
}
