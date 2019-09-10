package process;

import data.control.UserRulesControlMessage;
import data.messages.InputData;
import data.messages.MatchedMessage;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import process.state.StateDescriptors;

import java.util.HashMap;
import java.util.Map;

public class MatchingProcess extends BroadcastProcessFunction<InputData, UserRulesControlMessage, MatchedMessage> {

    @Override
    public void processElement(InputData inputData, ReadOnlyContext readOnlyContext, Collector<MatchedMessage> collector) throws Exception {
        if (inputData==null)
            return ;

        Iterable<Map.Entry<String, UserRulesControlMessage>> iterable = readOnlyContext
                .getBroadcastState(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR)
                .immutableEntries();

        Map<String,UserRulesControlMessage> userRulesMap = getUserRulesMap(iterable);

        if (userRulesMap==null)
            return;

        userRulesMap.values().stream()
                .flatMap(userRulesControlMessage -> userRulesControlMessage.getAlRules()
                        .stream()
                        .filter(u -> u.checkRule(inputData))
                        .map(u -> Tuple2.of(userRulesControlMessage.getAlUserId(), u)))
                .forEach(userIdAndRuleTuple -> collector.collect(new MatchedMessage(inputData,userIdAndRuleTuple.f0, userIdAndRuleTuple.f1)));
    }

    @Override
    public void processBroadcastElement(UserRulesControlMessage userRulesControlMessage, Context context, Collector<MatchedMessage> collector) throws Exception {
        if (userRulesControlMessage==null)
            return ;

        context.getBroadcastState(StateDescriptors.CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR).put(userRulesControlMessage.getAlUserId(), userRulesControlMessage);

    }

    private Map<String,UserRulesControlMessage> getUserRulesMap (Iterable<Map.Entry<String, UserRulesControlMessage>> iterable) {
        int size = 0;
        Map<String, UserRulesControlMessage> map = new HashMap<>();
        for (Map.Entry<String, UserRulesControlMessage> entry : iterable) {
            ++size;
            map.put(entry.getKey(), entry.getValue());
        }

        if (size==0)
            return null;
        else
            return map;
    }


}
