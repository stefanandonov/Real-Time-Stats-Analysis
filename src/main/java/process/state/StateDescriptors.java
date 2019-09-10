package process.state;

import data.control.UserRulesControlMessage;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.kafka.common.protocol.types.Field;

public class StateDescriptors {

    private static final String BROADCAST_STATE_NAME = "user-rules";

    public static final MapStateDescriptor<String, UserRulesControlMessage> CONTROL_MESSAGE_BROADCAST_STATE_DESCRIPTOR =
            new MapStateDescriptor<>(
                    BROADCAST_STATE_NAME,
                    TypeInformation.of(new TypeHint<String>() {}),
                    TypeInformation.of(new TypeHint<UserRulesControlMessage>() {})
            );
}
