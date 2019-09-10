package test.source;

import com.google.gson.Gson;
import data.control.UserRulesControlMessage;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import test.data.TestControlData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControlMessageSource implements SourceFunction<UserRulesControlMessage> {
    static Gson gson = new Gson();
    @Override
    public void run(SourceContext<UserRulesControlMessage> sourceContext) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(TestControlData.ControlData));
        List<UserRulesControlMessage> controlMessages = arrayList.stream()
                .map(s -> {
                    try {
                        return gson.fromJson(s, UserRulesControlMessage.class);
                    } catch (Exception e) {
                        return new UserRulesControlMessage();
                    }
                })
                .collect(Collectors.toList());


        controlMessages.stream()
                .forEach(c ->
                {
                    try {
                        //Thread.sleep(5000);
                        sourceContext.collect(c);
//                        pr.println(gson.toJson(us));
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void cancel() {

    }
}
