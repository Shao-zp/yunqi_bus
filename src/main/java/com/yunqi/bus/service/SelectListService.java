package com.yunqi.bus.service;

import com.yunqi.bus.mapper.SelectListMapper;
import com.yunqi.bus.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectListService {

    @Autowired
    SelectListMapper selectListMapper;

    public List<Bus> getCompanyList(){
        return selectListMapper.getCompanyList();
    }

    public List<Bus> getRouteList(String company){
        return selectListMapper.getRouteList(company);
    }

    public List<Bus> getVehicleList(String company,String route){
        return selectListMapper.getVehicleList(company,route);
    }

    public List<Bus> getPoint(String company,String route){
        return selectListMapper.getPoint(company,route);
    }

    public List<Bus> getRealPoint(String company,String route,String vehicle){
        return selectListMapper.getRealPoint(company,route,vehicle);
    }
}
