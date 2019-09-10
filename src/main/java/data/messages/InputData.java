package data.messages;

import com.google.gson.Gson;
import org.json.JSONObject;

public class InputData {
    JSONObject jsonObject;
    Long timestampMs;

    public InputData(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public InputData(String jsonString) {
        try {
            this.jsonObject = new JSONObject(jsonString);
        }
        catch (Exception e) {
            System.out.println(jsonString);
        }
    }

    public InputData(byte[] inputByteArray) {
        this.jsonObject = new JSONObject(new String(inputByteArray));
    }

    public void setField(String field, String value) {
        this.jsonObject.put(field, value);
    }

    public boolean hasFields(String fieldName) {
        return jsonObject.has(fieldName);
    }

    public String getValue(String fieldName) {

        return jsonObject.getString(fieldName);
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }


    public Long getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(Long timestampMs) {
        this.timestampMs = timestampMs;
    }
}
