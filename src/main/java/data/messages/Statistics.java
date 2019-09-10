package data.messages;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;
    @SerializedName("median")
    @Expose
    private Double median;
    @SerializedName("average")
    @Expose
    private Double average;
    @SerializedName("q1")
    @Expose
    private Double q1;
    @SerializedName("q3")
    @Expose
    private Double q3;

    public Statistics(Long count, Double min, Double max, Double median, Double average, Double q1, Double q3) {
        this.count = count;
        this.min = min;
        this.max = max;
        this.median = median;
        this.average = average;
        this.q1 = q1;
        this.q3 = q3;
    }

    public Double getQ1() {
        return q1;
    }

    public void setQ1(Double q1) {
        this.q1 = q1;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getQ3() {
        return q3;
    }

    public void setQ3(Double q3) {
        this.q3 = q3;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMedian() {
        return median;
    }

    public void setMedian(Double median) {
        this.median = median;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,Statistics.class);
    }
}