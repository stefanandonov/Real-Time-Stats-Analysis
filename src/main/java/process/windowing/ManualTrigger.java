package process.windowing;

import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

public class ManualTrigger<T> extends Trigger<T, TimeWindow> {

    private boolean eventTime;

    public ManualTrigger(boolean eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public TriggerResult onElement(T element, long timestamp, TimeWindow window, TriggerContext ctx) {
        if (eventTime) {
            if (window.maxTimestamp() <= ctx.getCurrentWatermark()) {
                // if the watermark is already past the window fire immediately
                return TriggerResult.FIRE;
            } else {
                ctx.registerEventTimeTimer(window.maxTimestamp());
                return TriggerResult.CONTINUE;
            }
        } else {
            ctx.registerProcessingTimeTimer(window.maxTimestamp());
            return TriggerResult.CONTINUE;
        }
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) {
        return eventTime ? TriggerResult.CONTINUE : TriggerResult.FIRE;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) {
        return eventTime ? TriggerResult.FIRE : TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) {
        if (eventTime) {
            ctx.deleteEventTimeTimer(window.maxTimestamp());
        } else {
            ctx.deleteProcessingTimeTimer(window.maxTimestamp());
        }
    }

    @Override
    public boolean canMerge() {
        return true;
    }

    @Override
    public void onMerge(TimeWindow window, OnMergeContext ctx) {
        long windowMaxTimestamp = window.maxTimestamp();
        if (eventTime && windowMaxTimestamp > ctx.getCurrentWatermark()) {
            ctx.registerEventTimeTimer(windowMaxTimestamp);
        } else if (windowMaxTimestamp > ctx.getCurrentProcessingTime()) {
            ctx.registerProcessingTimeTimer(windowMaxTimestamp);
        }
    }
}
