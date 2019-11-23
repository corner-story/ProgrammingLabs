package com.se.device.controller;

import com.se.device.entity.Device;
import com.se.device.entity.DeviceBorrow;
import com.se.device.service.BollowService;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*

    device的crud
    这里所有的url参照restful风格

*/


@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BollowService bollowService;


    //获取所有的device信息
    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllDevices(@RequestParam int page, @RequestParam int limit){
        //I should build service, but it's late
        int begin = (page-1)*limit;
        int end = limit;
        String count = String.valueOf(deviceService.count());
        List<Device> devices = deviceService.findAllByPage(begin, end);
        JsonResult<List<Device>> jsonResult = new JsonResult(devices);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }


    //获取单个device
    @RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getOneDevice(@PathVariable Integer id){
        Device device = deviceService.findOneById(id);
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "获取成功");
        res.put("data", device);

        return res;
    }


    //删除一个device
    @RequestMapping(value = "/devices/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteOneDevice(@PathVariable Integer id){
        HashMap<String, Object> res = new HashMap<>();

        if(id > 0){
            deviceService.deleteById(id);
            res.put("code", "200");
            res.put("msg", "删除成功");
        }else{
            res.put("code", "204");       //no content
            res.put("msg", "删除失败,ID错误!");
        }
        return res;
    }


    //更新一个device
    @RequestMapping(value = "/devices", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateOneDevice(@RequestBody Device device){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "update success!");
        try{
            deviceService.save(device);
            deviceService.flush();
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", "update error!");
        }

        return res;
    }



    //新增一个device
    @RequestMapping(value = "/devices", method = RequestMethod.POST)
    @ResponseBody
    public Object insertOneDevice(@RequestBody Device device){
        HashMap<String, Object> res = new HashMap<>();
        try {
            deviceService.save(device);
            res.put("code", "200");
            res.put("msg", "insert device successful!");
        }catch (Exception e){
            res.put("code", "500");
            res.put("msg", e.toString());
        }
        return res;
    }



    //获取update-device页面html
    @RequestMapping(value = "/updatedevice/{id}")
    public String updatedevice(@PathVariable Integer id, Model model){
        Device device = deviceService.findOneById(id);

        model.addAttribute("device", device);
        return "index/update_device";
    }


    //获取details_device页面html, device的status信息, 转借, 损坏, 报废
    @GetMapping(value = "/detailsdevice/{id}")
    public String detailsDevice(@PathVariable Integer id, Model model){
        Device device = deviceService.findOneById(id);
        model.addAttribute("device", device);

        if(device.getStatus() == null){
            return "device/details_device";
        }

        //设备转借
        List<DeviceBorrow> deviceBorrows = new ArrayList<>();
        if(device.getStatus().equals("借出")){
            deviceBorrows = bollowService.findAllByDeviceId(id);

            model.addAttribute("borrow", deviceBorrows);
            return "device/details_borrow_device";
        }


        return "device/details_device";
    }
}
