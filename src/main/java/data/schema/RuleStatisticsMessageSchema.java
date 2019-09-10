package data.schema;

import com.google.gson.Gson;
import data.messages.RuleStatisticsMessage;
import org.apache.flink.api.common.serialization.SerializationSchema;

public class RuleStatisticsMessageSchema implements SerializationSchema<RuleStatisticsMessage> {
    @Override
    public byte[] serialize(RuleStatisticsMessage ruleStatisticsMessage) {
        return new Gson().toJson(ruleStatisticsMessage, RuleStatisticsMessage.class).getBytes();

    }
}
