package net.gesher.rides.server.dal;

public class ExpressionBuilderHelper {
    private String fieldName;
    private Class fieldType;
    private Object value;
    private String predicateName;

    public Object getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(Object additionalValue) {
        this.additionalValue = additionalValue;
    }

    private Object additionalValue;

    public static final String PREDICATE_EQUALS = "equals";
    public static final String PREDICATE_GREATER_THAN = "gt";
    public static final String PREDICATE_LESS_THAN = "lt";
    public static final String PREDICATE_BETWEEN = "between";

    public String getFieldName() {
        return fieldName;
    }

    public String getPredicateName() {
        return predicateName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ExpressionBuilderHelper(String fieldName, Class fieldType, Object value, String predicateName) {
        this.predicateName = predicateName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.value = value;
    }


}
