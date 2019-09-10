package data.messages;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RuleStatisticsMessage {

    @SerializedName("al_user_id")
    private String alUserId;

    @SerializedName("al_rule_id")
    private String alRuleId;

    @SerializedName("al_timestamp")
    private Long alTimestamp;

    @SerializedName("al_stats")
    private Statistics alStats;

    @SerializedName("al_rule_description")
    private List<String> alRuleDescription;

    public RuleStatisticsMessage(String alUserId, String alRuleId, Statistics alStats, List<String> alRuleDescription) {
        this.alUserId = alUserId;
        this.alRuleId = alRuleId;
        this.alTimestamp = alTimestamp;
        this.alStats = alStats;
        this.alRuleDescription = alRuleDescription;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,RuleStatisticsMessage.class);
    }

    public Long getAlTimestamp() {
        return alTimestamp;
    }

    public void setAlTimestamp(Long alTimestamp) {
        this.alTimestamp = alTimestamp;
    }
}
