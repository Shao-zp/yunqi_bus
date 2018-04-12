package com.yunqi.bus.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Component
public class Bus {

    @NotNull
    private String route;

    @NotNull
    private String busNum;

    @NotNull
    private Double capacity;

    @NotNull
    private Date date;

    @NotNull
    private String month;

    @NotNull
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    //    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
}
