package process.aggregations;

import data.messages.MatchedMessage;
import data.messages.RuleStatisticsMessage;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class RuleAggregationFunction implements AggregateFunction<MatchedMessage, RuleAccumulator, Tuple2<RuleStatisticsMessage,String>> {

    @Override
    public RuleAccumulator createAccumulator() {
        return new RuleAccumulator();
    }

    @Override
    public RuleAccumulator add(MatchedMessage matchedMessage, RuleAccumulator ruleAccumulator) {
        return ruleAccumulator.add(matchedMessage);
    }

    @Override
    public Tuple2<RuleStatisticsMessage,String> getResult(RuleAccumulator ruleAccumulator) {
        return ruleAccumulator.getResult();
    }

    @Override
    public RuleAccumulator merge(RuleAccumulator ruleAccumulator, RuleAccumulator acc1) {
        return ruleAccumulator.merge(acc1);
    }
}
