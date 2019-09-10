package data.schema;

import com.google.gson.Gson;
import data.control.UserRulesControlMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.lang.reflect.Type;

public class UserRulesControlMessageSchema implements DeserializationSchema<UserRulesControlMessage> {
    @Override
    public UserRulesControlMessage deserialize(byte[] bytes) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new String(bytes), UserRulesControlMessage.class);
    }

    @Override
    public boolean isEndOfStream(UserRulesControlMessage userRulesControlMessage) {
        return false;
    }

    @Override
    public TypeInformation<UserRulesControlMessage> getProducedType() {
        return TypeInformation.of(new TypeHint<UserRulesControlMessage>() {
        });
    }
}
