package process.aggregations;

import com.tdunning.math.stats.TDigest;
import com.tdunning.math.stats.TreeDigest;
import data.control.AlRule;
import data.messages.InputData;
import data.messages.MatchedMessage;
import data.messages.RuleStatisticsMessage;
import data.messages.Statistics;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.DoubleSummaryStatistics;
import java.util.List;

public class RuleAccumulator {

    private DoubleSummaryStatistics summaryStatistics;
    private TDigest tDigest;

    private static final double MEDIAN = 0.5;
    private static final double Q1 = 0.25;
    private static final double Q3 = 0.75;
    private static final double DIGEST_COMPRESSION = 100;

    private String statsOutputTopic;
    private String ruleId;
    private String userId;
    private List<String> ruleDescription;

    RuleAccumulator() {
        summaryStatistics = new DoubleSummaryStatistics();
        tDigest = new TreeDigest(DIGEST_COMPRESSION);
    }

    public RuleAccumulator add(MatchedMessage matchedMessage) {
        AlRule rule = matchedMessage.getUserRule();
        InputData inputData = matchedMessage.getInputData();

        if (userId == null)
            userId = matchedMessage.getUserId();

        Double value = Double.parseDouble(inputData.getValue(rule.getAlStatsProperty()));

        summaryStatistics.accept(value);
        tDigest.add(value);

        if (statsOutputTopic == null)
            this.statsOutputTopic = rule.getAlRuleOutputTopic();

        if (this.ruleId == null)
            ruleId = rule.getAlRuleid();

        if (this.ruleDescription == null) {
            ruleDescription = rule.getRulesDescription();
        }

        return this;
    }

    public Tuple2<RuleStatisticsMessage,String> getResult() {
        Statistics statistics = new Statistics(
                summaryStatistics.getCount(),
                summaryStatistics.getMin(),
                summaryStatistics.getMax(),
                tDigest.quantile(MEDIAN),
                summaryStatistics.getAverage(),
                tDigest.quantile(Q1),
                tDigest.quantile(Q3));

        return Tuple2.of(new RuleStatisticsMessage(userId, ruleId, statistics, ruleDescription),statsOutputTopic);
    }

    public RuleAccumulator merge(RuleAccumulator acc1) {
        this.summaryStatistics.combine(acc1.summaryStatistics);
        this.tDigest.add(acc1.tDigest);
        return this;
    }
}
