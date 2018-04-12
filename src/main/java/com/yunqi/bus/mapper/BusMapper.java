package com.yunqi.bus.mapper;

import com.yunqi.bus.model.Bus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusMapper {
    List<Bus> capacity();
}
