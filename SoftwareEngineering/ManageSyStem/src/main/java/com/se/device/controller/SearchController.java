package com.se.device.controller;


import com.se.device.entity.Device;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
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

        int begin = (page-1)*limit;
        int end = limit;

        StringBuilder sb = new StringBuilder("select * from device where 1=1 ");
        if(info != null && info != ""){
            sb.append(" and (name like '%").append(info).append("%' or location like '%").append(info).append("%' ");
            sb.append(" or belong_dp_name like '%").append(info).append("%')");
        }
        if(begin_time != null && begin_time != ""){
            sb.append(" and create_time >= '").append(begin_time).append("'");
        }
        if(end_time != null && end_time != ""){
            sb.append(" and create_time <= '").append(end_time).append("'");
        }

        //分页和order by
        String count = sb.toString();
        sb.append(" order by create_time desc, id desc limit ").append(begin).append(", ").append(end);

        Query query = entityManager.createNativeQuery(sb.toString(), Device.class);
        Query queryCount = entityManager.createNativeQuery(count, Device.class);

        JsonResult<Object> jsonResult = new JsonResult(query.getResultList());
        jsonResult.setCount(String.valueOf(queryCount.getResultList().size()));
        jsonResult.setCode("0");
        entityManager.close();
        return jsonResult;
    }

}
