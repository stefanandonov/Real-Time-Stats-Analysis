
package data.control;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.messages.InputData;



public class AlFilter implements Serializable
{

    private static final String CATEGORICAL = "string";
    private static final String LESS_OR_EQ = "LESS_OR_EQ";
    private static final String LESS = "LESS";
    private static final String GREATER_OR_EQ = "GREATER_OR_EQ";
    private static final String GREATER = "GREATER" ;
    private static final String EQ = "EQ";
    private static final String CONTAINS = "CONTAINS";
    @SerializedName("al_filter_property")
    @Expose
    private String alFilterProperty;
    @SerializedName("al_filter_property_type")
    @Expose
    private String alFilterPropertyType;
    @SerializedName("al_operator")
    @Expose
    private String alOperator;
    @SerializedName("al_filter_value")
    @Expose
    private String alFilterValue;
    private final static long serialVersionUID = -308020916531049471L;

    public String getAlFilterProperty() {
        return alFilterProperty;
    }

    public void setAlFilterProperty(String alFilterProperty) {
        this.alFilterProperty = alFilterProperty;
    }

    public String getAlFilterPropertyType() {
        return alFilterPropertyType;
    }

    public void setAlFilterPropertyType(String alFilterPropertyType) {
        this.alFilterPropertyType = alFilterPropertyType;
    }

    public String getAlOperator() {
        return alOperator;
    }

    public void setAlOperator(String alOperator) {
        this.alOperator = alOperator;
    }

    public String getAlFilterValue() {
        return alFilterValue;
    }

    public void setAlFilterValue(String alFilterValue) {
        this.alFilterValue = alFilterValue;
    }

    public boolean checkInputData (InputData inputData) {

        if (!inputData.hasFields(alFilterProperty))
            return true;

        if (alFilterPropertyType.equals(CATEGORICAL)) {
            String value = inputData.getValue(alFilterProperty);
            if (alOperator.equals(EQ))
                return value.equals(alFilterValue);
            else if (alOperator.contains(alFilterValue))
                return value.contains(alFilterProperty);
        }else {
            Double value = Double.parseDouble(inputData.getValue(alFilterProperty));
            Double filterValue = Double.parseDouble(alFilterValue);
            if (alOperator.equals(LESS_OR_EQ))
                return value<=filterValue;
            if (alOperator.equals(LESS))
                return value<filterValue;
            if (alOperator.equals(GREATER_OR_EQ))
                return value>=filterValue;
            if (alOperator.equals(GREATER))
                return value>filterValue;
            if (alOperator.equals(EQ))
                return value.equals(filterValue);
        }

        return false;
    }

    public String getFilterDescription() {

        if (alOperator.equals(EQ))
            return "Field "+ alFilterProperty + " should be EQUAL with: " + alFilterValue;
        if (alOperator.equals(CONTAINS))
            return "Field "+ alFilterProperty + " should CONTAIN the substring: " + alFilterValue;
        if (alOperator.equals(LESS_OR_EQ))
            return "Field "+ alFilterProperty + " should be LESS OR EQUAL THAN: " + alFilterValue;
        if (alOperator.equals(LESS))
            return "Field "+ alFilterProperty + " should be LESS THAN: " + alFilterValue;
        if (alOperator.equals(GREATER_OR_EQ))
            return "Field "+ alFilterProperty + " should be GREATER OR EQUAL THAN: " + alFilterValue;
        if (alOperator.equals(GREATER))
            return "Field "+ alFilterProperty + " should be GREATER THAN: " + alFilterValue;
        else
            return null;
    }
}
