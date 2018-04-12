package com.yunqi.bus.mapper;

import com.yunqi.bus.model.Bus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SelectListMapper {

    List<Bus> getCompanyList();

    List<Bus> getRouteList(@Param("company") String company);

    List<Bus> getVehicleList(@Param("company") String company, @Param("route") String route);

    List<Bus> getPoint(@Param("company") String company, @Param("route") String route);

    List<Bus> getRealPoint(@Param("company") String company, @Param("route")String route, @Param("vehicle")String vehicle);
}
