package data.schema;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;

public class TopicTupleSerializationSchema<K> implements KeyedSerializationSchema<Tuple2<K, String>> {

    SerializationSchema<K> valueSerializationSchema;

    public TopicTupleSerializationSchema(SerializationSchema<K> valueSerializationSchema) {
        this.valueSerializationSchema = valueSerializationSchema;
    }

    @Override
    public byte[] serializeKey(Tuple2<K, String> tuple) {
        return new byte[0];
    }

    @Override
    public byte[] serializeValue(Tuple2<K, String> tuple) {
        return valueSerializationSchema.serialize(tuple.f0);

    }

    @Override
    public String getTargetTopic(Tuple2<K, String> tuple) {
        return tuple.f1;
    }
}