package com.yunqi.bus.service;

import com.yunqi.bus.mapper.BusMapper;
import com.yunqi.bus.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService{

    @Autowired
    BusMapper busMapper;

    public List<Bus> getBus(){
        return busMapper.capacity();
    }

}
