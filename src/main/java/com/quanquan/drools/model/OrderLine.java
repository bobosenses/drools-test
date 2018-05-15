package com.quanquan.drools.model;

public class OrderLine {

    //收货登记单线路信息
    private String info;

    //单据状态
    private String status;

    //是否允许进场
    private Boolean pass;

    //数量
    private Integer num;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "info='" + info + '\'' +
                ", status='" + status + '\'' +
                ", pass=" + pass +
                ", num=" + num +
                '}';
    }
}
