package data.messages;

import data.control.AlRule;
import data.control.UserRulesControlMessage;
import process.windowing.IConfigurableWindow;

import java.util.Map;

public class MatchedMessage implements IConfigurableWindow<InputData> {

    private InputData inputData;
    private String userId;
    private AlRule userRule;

    public MatchedMessage(InputData inputData, String userId, AlRule useRule) {
        this.inputData = inputData;
        this.userId = userId;
        this.userRule=useRule;
    }


    public InputData getInputData() {
        return inputData;
    }

    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

    public AlRule getUserRule() {
        return userRule;
    }

    public void setUserRulesMap(AlRule userRule) {
        this.userRule = userRule;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserRule(AlRule userRule) {
        this.userRule = userRule;
    }

    @Override
    public String toString() {
        return "MatchedMessage{" +
                "inputData=" + inputData +
                ", userId='" + userId + '\'' +
                ", userRule=" + userRule +
                '}';
    }

    @Override
    public InputData getElement() {
        return inputData;
    }


    @Override
    public Long getWindowSize() {
        return userRule.getAlConfig().getAlWindowSize();
    }

    @Override
    public String getWindowType() {
        return userRule.getAlConfig().getAlWindowType();
    }

    @Override
    public Long getWindowSlide() {
        return userRule.getAlConfig().getAlWindowSlide();
    }
}
