package test.data;

import java.util.Arrays;

public class TestControlData {


    public static String[] ControlData = {
            //"{\"al_user_id\":\"User_Stefan\",\"al_rules\":[{\"al_ruleid\":\"PM10rule\",\"al_filters\":[{\"al_filter_property\":\"type\",\"al_filter_property_type\":\"string\",\"al_operator\":\"EQ\",\"al_filter_value\":\"pm10\"},{\"al_filter_property\":\"value\",\"al_filter_property_type\":\"numerical\",\"al_operator\":\"GREATER\",\"al_filter_value\":\"10\"}],\"al_rule_output_topic\":\"user123-pm10\",\"al_stats_property\":\"value\",\"al_config\":{\"al_window_type\":\"sliding\",\"al_window_size\":14400000,\"al_window_slide\":3600000}},{\"al_ruleid\":\"SO2rule\",\"al_filters\":[{\"al_filter_property\":\"type\",\"al_filter_property_type\":\"string\",\"al_operator\":\"EQ\",\"al_filter_value\":\"so2\"}],\"al_rule_output_topic\":\"user123-so2\",\"al_stats_property\":\"value\",\"al_config\":{\"al_window_type\":\"tumbling\",\"al_window_size\":14400000}}]}",
            //"{\"al_user_id\":\"Stefan\",\"al_rules\":[{\"al_ruleid\":\"rule1\",\"al_filters\":[{\"al_filter_property\":\"type\",\"al_filter_property_type\":\"string\",\"al_operator\":\"EQ\",\"al_filter_value\":\"co\"},{\"al_filter_property\":\"value\",\"al_filter_property_type\":\"numerical\",\"al_operator\":\"GREATER\",\"al_filter_value\":\"1\"}],\"al_rule_output_topic\":\"stefan-kafka\",\"al_stats_property\":\"value\",\"al_config\":{\"al_window_type\":\"tumbling\",\"al_window_size\":10800000,\"al_window_slide\":0}}]}",
            "{\"al_user_id\":\"User_123\",\"al_rules\":[{\"al_ruleid\":\"PM10rule\",\"al_filters\":[{\"al_filter_property\":\"type\",\"al_filter_property_type\":\"string\",\"al_operator\":\"EQ\",\"al_filter_value\":\"pm10\"},{\"al_filter_property\":\"value\",\"al_filter_property_type\":\"numerical\",\"al_operator\":\"GREATER\",\"al_filter_value\":\"100\"}],\"al_rule_output_topic\":\"user123-topic\",\"al_stats_property\":\"value\",\"al_config\":{\"al_window_type\":\"sliding\",\"al_window_size\":14400000,\"al_window_slide\":1800000}}]}"
    };

    public static void main(String[] args) {
        Arrays.stream(ControlData).forEach(System.out::println);
    }
}
