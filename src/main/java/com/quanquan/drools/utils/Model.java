package com.quanquan.drools.utils;

public class Model {

    //字段名
    private String field;
    //操作名
    private String searchOperator;
    //值
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSearchOperator() {
        return searchOperator;
    }

    public void setSearchOperator(String searchOperator) {
        this.searchOperator = searchOperator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
