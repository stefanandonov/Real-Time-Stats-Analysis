package data.control;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlConfig implements Serializable {

    @SerializedName("al_window_type")
    @Expose
    private String alWindowType;
    @SerializedName("al_window_size")
    @Expose
    private Long alWindowSize;
    @SerializedName("al_window_slide")
    @Expose
    private Long alTimewindowSlide;

    private final static long serialVersionUID = 2537271395955536063L;

    public String getAlWindowType() {
        return alWindowType;
    }

    public void setAlWindowType(String alWindowType) {
        this.alWindowType = alWindowType;
    }

    public Long getAlWindowSize() {
        return alWindowSize;
    }

    public void setAlWindowSize(Long alWindowSize) {
        this.alWindowSize = alWindowSize;
    }

    public Long getAlWindowSlide() {
        return alTimewindowSlide;
    }



    public AlConfig update(AlConfig alConfig) {
        return alConfig;
    }
}