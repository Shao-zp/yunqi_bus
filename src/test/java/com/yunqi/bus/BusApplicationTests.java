package com.yunqi.bus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunqi.bus.model.Bus;
import com.yunqi.bus.service.BusService;
import com.yunqi.bus.service.SelectListService;
import com.yunqi.bus.util.GetMinAndMax;
import com.yunqi.bus.util.NextDateAndMonth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusApplicationTests {

	@Autowired
	BusService busService;

	@Autowired
    SelectListService selectListService;

	@Test
	public void contextLoads() {

		List<Bus> bus = busService.getBus();

		for (Bus a :
			 bus) {
			System.out.println(a.getRoute()+" "+a.getBusNum()+" "+a.getDate()+" "+a.getCapacity());
		}

	}

    @Test
    public void test(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar minDay = Calendar.getInstance();
        minDay.set(2019,02,20);
        NextDateAndMonth nextDateAndMonth = new NextDateAndMonth();
	    Date x = nextDateAndMonth.nextDay(minDay.getTime(),1);

        Calendar maxDay = Calendar.getInstance();
        minDay.set(2021,02,20);
        List<String> xAxisDate = new ArrayList<>();
//        if (df.format(x.getTime()).equals(df.format(maxDay.getTime()))){
//            xAxisDate.add(x+"");
//        }else {
//            xAxisDate.add(nextDateAndMonth.nextDay(x.getTime(),1));
//        }

        xAxisDate.add(x+"");

        int i = 1;


    }

    private int nextDay(int dtDay){
        //int year = dtDay/12;
        //int nextMonth = dtDay%100;
        //if (nextMonth >= 12) {
        //    ++year;
        //    nextMonth = 0;
        //}
        //return (year * 100 + nextMonth + 1);
//        Date xx = new Date();
//        xx.
        return dtDay;
    }

	@Test
    public void getPoint( ){
	    String company = "公交一公司";
        String route="10";
//        String vehicle="F1-9970";
        String vehicle="F1-9742";
        List<Bus> companyList;
        if (vehicle.equals("NIL"))
            companyList = selectListService.getPoint(company,route);
        else
            companyList = selectListService.getRealPoint(company,route,vehicle);

        // key = bus num, value = points[n] * coordinate[2]
        TreeMap<String, ArrayList<String[]>>  busPointMap = new TreeMap<String,ArrayList<String[]>>();

        ArrayList<String> xList = new ArrayList<>();
        for(Bus b : companyList){
            ArrayList<String[]> pointArray = busPointMap.get(b.getBusNum());
            String[] newPoint = new String[2];
            if (b.getDate()==null){
                newPoint[0] = b.getMonth();
                newPoint[1] = b.getCapacity().toString();
            }else {
//                nextDay(b.getDate(),1);
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                newPoint[0] = df.format(b.getDate());
                newPoint[1] = b.getCapacity().toString();
            }

            if(pointArray == null) {
                ArrayList<String[]> newPointArray = new ArrayList<String[]>();
                newPointArray.add(newPoint);
                busPointMap.put(b.getBusNum(), newPointArray);
            } else {
                pointArray.add(newPoint);
            }
            xList.add(newPoint[0]);
        }

        GetMinAndMax getMinAndMax = new GetMinAndMax(xList).invoke();
        int min = getMinAndMax.getMin();
        int max = getMinAndMax.getMax();

        System.out.println(min+" "+max);
//        List<String> xDateList = new ArrayList(new TreeSet(xList));
//        for (String a :
//                xList) {
//            System.out.print(a+" ");
//        }
//        System.out.println();
//        for (String a :
//                xDateList) {
//            System.out.print(a+" ");
//        }
//        System.out.println();
//        System.out.println(xDateList.get(0)+" "+xDateList.get(xDateList.size()-1));

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

        JSONArray jsonDateList = new JSONArray();
//        jsonDateList.add(xDateList);
        JSONObject responseObject = new JSONObject();
//        responseObject.put("xDate",jsonDateList);
        responseObject.put("company", company);
        responseObject.put("route", route);
        responseObject.put("vehicles", vehiclesArray);

//        System.out.println(jsonArray.get(1));
    }

    @Test
    public void getCompany(){
        List<Bus> company = selectListService.getCompanyList();

        for (Bus b : company){
            System.out.println(b.getCompany());
        }

        company = selectListService.getPoint("公交一公司","102");

        for (Bus b : company){
            System.out.println(b.getMonth()+" "+b.getCapacity()+" "+b.getBusNum());
        }

    }


}
