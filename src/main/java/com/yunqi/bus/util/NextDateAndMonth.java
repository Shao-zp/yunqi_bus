package com.yunqi.bus.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NextDateAndMonth {

    public Date nextDay(Date dateTime/*待处理的日期*/, int n/*加减天数*/) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.util.Calendar calStart = java.util.Calendar.getInstance();
        calStart.setTime(dateTime);
        calStart.add(java.util.Calendar.DAY_OF_WEEK, n);
        System.out.println("-----------");
        System.out.println(df.format(calStart.getTime()));
        return calStart.getTime();
    }

    public int nextMonth(int dtMonth) {
        int year = dtMonth/100;
        int nextMonth = dtMonth%100;
        if (nextMonth >= 12) {
            ++year;
            nextMonth = 0;
        }
        return (year * 100 + nextMonth + 1);
    }
}
