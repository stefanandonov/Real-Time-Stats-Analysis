package factories;


import data.control.UserRulesControlMessage;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import settings.ProjectSettings;
import test.source.ControlMessageSource;


public class SourceControlStreamFactory {

    public static DataStream<UserRulesControlMessage> getStream(ParameterTool parameterTool, StreamExecutionEnvironment env) {

        boolean testMode = parameterTool.getBoolean(
                ProjectSettings.TEST_MODE,
                ProjectSettings.DEFAULT_TEST_MODE
        );

        if (testMode) {
            return env.addSource(new ControlMessageSource());
        } else {
            return KafkaDataStream.getControlStream(parameterTool, env);
        }
    }
}
