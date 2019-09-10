
package data.control;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRulesControlMessage implements Serializable
{

    @SerializedName("al_user_id")
    @Expose
    private String alUserId;
    @SerializedName("al_rules")
    @Expose
    private List<AlRule> alRules = null;
    private final static long serialVersionUID = -8331182543423686410L;

    public String getAlUserId() {
        return alUserId;
    }

    public void setAlUserId(String alUserId) {
        this.alUserId = alUserId;
    }

    public List<AlRule> getAlRules() {
        return alRules;
    }

    public void setAlRules(List<AlRule> alRules) {
        this.alRules = alRules;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,UserRulesControlMessage.class);
    }
}
