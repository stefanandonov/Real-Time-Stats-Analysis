package data;

import data.control.UserRulesControlMessage;
import data.messages.InputData;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import settings.ProjectSettings;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class TimestampsAndWatermarksAssigner {
    public static DataStream<InputData> assignProtocolMessagesTimestamps(ParameterTool parameterTool, DataStream<InputData> dataStream) {
        Long maxOutOfOrderliness = parameterTool.getLong(ProjectSettings.DEFAULT_OUT_OF_ORDER_TIME_NAME, ProjectSettings.DEFAULT_OUT_OF_ORDER_TIME);

        String timestampProperty = parameterTool.get(ProjectSettings.TIMESTAMP_PROPERTY_NAME, ProjectSettings.DEFAULT_TIMESTAMP_PROPERTY);
        return dataStream
                .map(s -> s)
                .assignTimestampsAndWatermarks(
                        new BoundedOutOfOrdernessPunctuatedWatermark<InputData>(maxOutOfOrderliness) {
                            @Override
                            public long extractTimestamp(InputData element) {
                                ZonedDateTime zonedDateTime = ZonedDateTime.parse(element.getValue(timestampProperty));
                                element.setTimestampMs(zonedDateTime.toInstant().toEpochMilli());
                                return zonedDateTime.toInstant().toEpochMilli();
                            }
                        });
    }

    public static DataStream<UserRulesControlMessage> assignControlMessagesTimestamps(DataStream<UserRulesControlMessage> controlStream) {

        return controlStream
                .map(s -> s)
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserRulesControlMessage>() {
                    @Override
                    public long extractAscendingTimestamp(UserRulesControlMessage element) {
                        return Long.MAX_VALUE;
                    }
                });
    }
}
