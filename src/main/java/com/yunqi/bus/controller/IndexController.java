package com.yunqi.bus.controller;

import com.yunqi.bus.service.BusService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(BusController.class);

    @Autowired
    private BusService busService;

    @RequestMapping("/")
    public String home() {
        return "redirect:/echarts.html";
    }

}
