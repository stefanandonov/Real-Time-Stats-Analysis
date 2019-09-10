package factories;

import data.messages.InputData;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import settings.ProjectSettings;
import test.source.DataSource;

public class SourceDataStreamFactory {

    public static DataStream<InputData> getStream(ParameterTool parameterTool, StreamExecutionEnvironment env) {

        boolean testMode = parameterTool.getBoolean(
                ProjectSettings.TEST_MODE,
                ProjectSettings.DEFAULT_TEST_MODE
        );

        if (testMode) {
            return env.addSource(new DataSource());
        } else {
            return KafkaDataStream.getDataStream(parameterTool, env);
        }
    }
}
