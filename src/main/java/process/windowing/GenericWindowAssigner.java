package process.windowing;


import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GenericWindowAssigner <T extends IConfigurableWindow> extends WindowAssigner<T,TimeWindow> {

    private boolean eventTime;
    private String TUMBLING = "tumbling";
    private String SLIDING = "sliding";

    public GenericWindowAssigner(boolean eventTime) {
        this.eventTime = eventTime;
    }
    @Override
    public Collection<TimeWindow> assignWindows(T element, long timestamp, WindowAssignerContext context) {

        String windowType = element.getWindowType();
        if (windowType.equals(TUMBLING)) {
            if (!eventTime) {
                timestamp=context.getCurrentProcessingTime();
            }
            long windowSize = element.getWindowSize();
            long start = (timestamp / windowSize) * windowSize;
            long end = start + windowSize;
            TimeWindow timeWindow =  new TimeWindow(start, end);
            return Collections.singletonList(timeWindow);
        } else { //SLIDING WINDOWS
            if (eventTime) {
                if (timestamp == Long.MIN_VALUE) {
                    throw new RuntimeException("Record has Long.MIN_VALUE timestamp (= no timestamp marker). " +
                            "Is the time characteristic set to 'ProcessingTime', or did you forget to call " +
                            "'DataStream.assignTimestampsAndWatermarks(...)'?");
                }
            } else {
                timestamp = context.getCurrentProcessingTime();
            }
            return createSlidingWindows(element,timestamp);
        }
    }

    private List<TimeWindow> createSlidingWindows(T element, long timestamp) {
        long windowSize = element.getWindowSize();
        long slide = element.getWindowSlide();
        List<TimeWindow> windows = new ArrayList<>();
        long start = ((timestamp + slide - windowSize) / slide) * slide;
        while (start <= timestamp) {
            windows.add(new TimeWindow(start, start + windowSize));
            start += slide;
        }
        return windows;
    }

    @Override
    public Trigger<T, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
        return new ManualTrigger<>(eventTime);
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
        return new TimeWindow.Serializer();
    }

    @Override
    public boolean isEventTime() {
        return eventTime;
    }
}
