package data.schema;

import com.google.gson.Gson;
import data.messages.InputData;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class InputDataSchema implements DeserializationSchema<InputData> {
    @Override
    public InputData deserialize(byte[] bytes) throws IOException {
        return new InputData(bytes);
    }

    @Override
    public boolean isEndOfStream(InputData inputData) {
        return false;
    }

    @Override
    public TypeInformation<InputData> getProducedType() {
        return TypeInformation.of(new TypeHint<InputData>() {
        });
    }
}
