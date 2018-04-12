package com.yunqi.bus.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunqi.bus.model.Bus;
import com.yunqi.bus.service.BusService;
import com.yunqi.bus.service.SelectListService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class BusController {
    private static Logger logger = Logger.getLogger(BusController.class);

    @Autowired
    private BusService busService;

    @Autowired
    private SelectListService selectListService;

//    @RequestMapping("/getBus")
//    public JSONArray getBus(){
//        List<Bus> bus = busService.getBus();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject;
//
//        for (Bus b : bus){
//            jsonObject = new JSONObject();
//            jsonObject.put("date",b.getDate());
//            jsonObject.put("cap",b.getCapacity());
//            jsonArray.put(jsonObject);
//        }
//        System.out.println(jsonArray.toString());
//        return jsonArray;
//    }

    @PostMapping("/get_companyList")
    public JSONArray getCompanyList() {
        List<Bus> companyList = selectListService.getCompanyList();
        JSONArray jsonArray = new JSONArray();
        //JSONObject jsonObject;
        for(Bus b : companyList){
            //jsonObject = new JSONObject();
            //jsonObject.put("company",b.getCompany());
            jsonArray.add(b.getCompany());
        }
        return jsonArray;
    }

    @PostMapping("/get_routeList")
    public JSONArray getRouteList(String company) {
        List<Bus> companyList = selectListService.getRouteList(company);
        JSONArray jsonArray = new JSONArray();
        //JSONObject jsonObject;
        for(Bus b : companyList){
            //jsonObject = new JSONObject();
            //jsonObject.put("route",b.getRoute());
            jsonArray.add(b.getRoute());
        }
        return jsonArray;
    }

    @PostMapping("/get_vehicleList")
    public JSONArray getVehicleList(String company,String route) {
        List<Bus> companyList = selectListService.getVehicleList(company,route);
        JSONArray jsonArray = new JSONArray();
        //JSONObject jsonObject;
        for(Bus b : companyList){
            //jsonObject = new JSONObject();
            //jsonObject.put("vehicle",b.getBusNum());
            jsonArray.add(b.getBusNum());
        }
        return jsonArray;
    }

    @PostMapping("/get_busCapacity4Daily")
    public JSONObject get_busCapacityByDaily(String company,String route, String vehicle){
        List<Bus> companyList;

        companyList = selectListService.getRealPoint(company,route,vehicle);

        // key = bus num, value = points[n] * coordinate[2]
        TreeMap<String, ArrayList<String[]>>  busPointMap = new TreeMap<>();
        ArrayList<String> xList = new ArrayList<>();

        for(Bus b : companyList){
            ArrayList<String[]> pointArray = busPointMap.get(b.getBusNum());
            String[] newPoint = new String[2];
            DateFormat df = new SimpleDateFormat("yyyyMMdd");

            newPoint[0] = df.format(b.getDate());
            newPoint[1] = b.getCapacity().toString();

            if (pointArray == null) {
                ArrayList<String[]> newPointArray = new ArrayList<>();
                newPointArray.add(newPoint);
                busPointMap.put(b.getBusNum(), newPointArray);
            } else {
                pointArray.add(newPoint);
            }
            xList.add(newPoint[0]);
        }

        List<String> xDateList = new ArrayList(new TreeSet(xList));

        JSONArray vehiclesArray = new JSONArray();
        /* {
                comnpany: xx
                route: xx
                vehicles: [
                    {
                        name: xx
                        points: [[x, y], ...]
                    }, ...
                ]
               xAxisData:[date...]
            }
         */
        Iterator<Map.Entry<String, ArrayList<String[]>>> it = busPointMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, ArrayList<String[]>> entry = it.next();
            String busNumber = entry.getKey();
            ArrayList<String[]> pointsList = entry.getValue();

            JSONObject jsonVehicle = new JSONObject();
            JSONArray jsonPoints = new JSONArray();

//            for(String[] p : (vehicle.equals("NIL") ? toPercentile(pointsList) : pointsList)) {
            for(String[] p : to256tile(pointsList)) {
                JSONArray point = new JSONArray();
                point.add(p[0]);
                point.add(p[1]);
                jsonPoints.add(point);
            }

            jsonVehicle.put("name", busNumber);
            jsonVehicle.put("points", jsonPoints);
            vehiclesArray.add(jsonVehicle);
        }
        //返回一个作为x轴的日期排序后的json数组

        JSONObject responseObject = new JSONObject();
        responseObject.put("xAxisData",xDateList);
        responseObject.put("company", company);
        responseObject.put("route", route);
        responseObject.put("vehicle", vehicle);
        responseObject.put("vehiclesData", vehiclesArray);

        return responseObject;
    }

    @PostMapping("/get_busCapacity4Monthly")
    public JSONObject get_busCapacityByMonthly(String company,String route){
        List<Bus> companyList;

        companyList = selectListService.getPoint(company,route);

        // key = bus num, value = points[n] * coordinate[2]
        TreeMap<String, ArrayList<String[]>>  busPointMap = new TreeMap<>();
        ArrayList<String> xList = new ArrayList<>();

        for(Bus b : companyList){
            ArrayList<String[]> pointArray = busPointMap.get(b.getBusNum());
            String[] newPoint = new String[2];
//            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            newPoint[0] = b.getMonth();
            newPoint[1] = b.getCapacity().toString();

            if (pointArray == null) {
                ArrayList<String[]> newPointArray = new ArrayList<>();
                newPointArray.add(newPoint);
                busPointMap.put(b.getBusNum(), newPointArray);
            } else {
                pointArray.add(newPoint);
            }
            xList.add(newPoint[0]);
        }

        List<String> xDateList = new ArrayList(new TreeSet(xList));
        JSONArray vehiclesArray = new JSONArray();
        /* {
                comnpany: xx
                route: xx
                vehicles: [
                    {
                        name: xx
                        points: [[x, y], ...]
                    }, ...
                ]
            }
         */
        Iterator<Map.Entry<String, ArrayList<String[]>>> it = busPointMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, ArrayList<String[]>> entry = it.next();
            String busNumber = entry.getKey();
            ArrayList<String[]> pointsList = entry.getValue();

            JSONObject jsonVehicle = new JSONObject();
            JSONArray jsonPoints = new JSONArray();

//            for(String[] p : (vehicle.equals("NIL") ? toPercentile(pointsList) : pointsList)) {
            for(String[] p : pointsList) {
                JSONArray point = new JSONArray();
                point.add(p[0]);
                point.add(p[1]);
                jsonPoints.add(point);
            }

            jsonVehicle.put("name", busNumber);
            jsonVehicle.put("points", jsonPoints);
            vehiclesArray.add(jsonVehicle);
        }

        JSONObject responseObject = new JSONObject();
        responseObject.put("xAxisData",xDateList);
        responseObject.put("company", company);
        responseObject.put("route", route);
        responseObject.put("vehiclesData", vehiclesArray);

        return responseObject;
    }

    private ArrayList<String[]> to256tile(ArrayList<String[]> rhs) {
        if(rhs.size() <= 257)
            return rhs;

        ArrayList<String[]> lhs = new ArrayList<>();
        float stepLength = rhs.size() / 256.00f;
        float cursor = 0;
        for(int i = 0; i < 256; ++i) {
            lhs.add(rhs.get((int)cursor));
            cursor += stepLength;
        }
        lhs.add(rhs.get(rhs.size() - 1));
        return lhs;
    }
}
