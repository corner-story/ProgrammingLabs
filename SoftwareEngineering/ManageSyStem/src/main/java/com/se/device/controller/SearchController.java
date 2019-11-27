package com.se.device.controller;


import com.se.device.entity.Device;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private DeviceService deviceService;

    @PersistenceContext
    private EntityManager entityManager;


    @RequestMapping("/search/devices")
    @ResponseBody
    public Object searchDevice(@RequestParam int page, @RequestParam int limit, @RequestParam String info, @RequestParam String begin_time, @RequestParam String end_time){
        String sql = "select * from device where id=1;";
        List<Device> data = deviceService.findAllBySql(sql);
        JsonResult<Object> jsonResult = new JsonResult(data);
        jsonResult.setCount("20");
        jsonResult.setCode("0");


        Query query = entityManager.createNativeQuery("select * from device", Device.class);

        for (Object item : query.getResultList()) {
            Device obj = (Device) item;
            System.out.println(obj.getName());
        }

        return jsonResult;
    }

}
