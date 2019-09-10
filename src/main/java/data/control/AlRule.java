
package data.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.messages.InputData;
import org.apache.kafka.clients.admin.AlterConfigsOptions;

public class AlRule implements Serializable
{

    @SerializedName("al_ruleid")
    @Expose
    private String alRuleid;
    @SerializedName("al_filters")
    @Expose
    private List<AlFilter> alFilters = null;
    @SerializedName("al_rule_output_topic")
    @Expose
    private String alRuleOutputTopic;
    @SerializedName("al_stats_property")
    @Expose
    private String alStatsProperty;

    @SerializedName("al_config")
    @Expose
    private AlConfig alConfig;

    private final static long serialVersionUID = -3390573753324615057L;

    public String getAlRuleid() {
        return alRuleid;
    }

    public void setAlRuleid(String alRuleid) {
        this.alRuleid = alRuleid;
    }

    public List<AlFilter> getAlFilters() {
        return alFilters;
    }

    public void setAlFilters(List<AlFilter> alFilters) {
        this.alFilters = alFilters;
    }

    public String getAlRuleOutputTopic() {
        return alRuleOutputTopic;
    }

    public void setAlRuleOutputTopic(String alRuleOutputTopic) {
        this.alRuleOutputTopic = alRuleOutputTopic;
    }

    public String getAlStatsProperty() {
        return alStatsProperty;
    }

    public void setAlStatsProperty(String alStatsProperty) {
        this.alStatsProperty = alStatsProperty;
    }

    public AlConfig getAlConfig() {
        return alConfig;
    }

    public void setAlConfig(AlConfig alConfig) {
        this.alConfig = alConfig;
    }

    public boolean checkRule (InputData inputData) {
        boolean filtersSatisfied = alFilters.stream().allMatch(filter -> filter.checkInputData(inputData));

        if (!filtersSatisfied)
            return false;

        return inputData.hasFields(alStatsProperty);
    }

    public List<String> getRulesDescription () {
        return alFilters.stream()
                .map(alFilter -> alFilter.getFilterDescription())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,AlRule.class);
    }
}
