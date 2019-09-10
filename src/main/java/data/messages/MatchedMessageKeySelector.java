package data.messages;

import data.messages.MatchedMessage;
import org.apache.flink.api.java.functions.KeySelector;

public class MatchedMessageKeySelector implements KeySelector<MatchedMessage, String> {
    @Override
    public String getKey(MatchedMessage matchedMessage) throws Exception {
        return matchedMessage.getUserId() + "~~" + matchedMessage.getUserRule().getAlRuleid();
    }
}
