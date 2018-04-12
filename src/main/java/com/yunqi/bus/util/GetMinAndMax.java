package com.yunqi.bus.util;

import java.util.ArrayList;

public class GetMinAndMax {
    private ArrayList<String> xList;
    private int min;
    private int max;

    public GetMinAndMax(ArrayList<String> xList) {
        this.xList = xList;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public GetMinAndMax invoke() {
        min = Integer.valueOf(xList.get(0));
        max = Integer.valueOf(xList.get(0));
        for (int i = 0 ; i<xList.size() ; i++){
            int a = Integer.parseInt(xList.get(i));
            if (a>max){
                max = a;
            }else {
                if (a<min){
                    min = a;
                }
            }
        }
        return this;
    }

}