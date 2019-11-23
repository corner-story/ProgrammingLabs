package com.se.device.controller;



import com.se.device.entity.Device;
import com.se.device.entity.DeviceBorrow;
import com.se.device.service.BollowService;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class BollowController {

    @Autowired
    private BollowService bollowService;

    @Autowired
    private DeviceService deviceService;

    //获取所有的设备转借信息
    @RequestMapping(value = "/bollow", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllBollows(@RequestParam int page, @RequestParam int limit, HttpSession session){
        Object id = session.getAttribute("id");
        Integer user_id = Integer.valueOf(String.valueOf(id));
        List<DeviceBorrow> deviceBorrows = bollowService.findAllByUserId(user_id);

        int begin = (page-1)*limit;
        int end = limit;

        String count = String.valueOf(deviceBorrows.size());
        List<DeviceBorrow> data = bollowService.findAllByUserIdToPage(user_id, begin, end);
        JsonResult<List<DeviceBorrow>> jsonResult = new JsonResult(data);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }



    //新增一个设备转借记录
    @RequestMapping(value = "/bollow", method = RequestMethod.POST)
    @ResponseBody
    public Object insertOneDevice(@RequestBody DeviceBorrow deviceBorrow){
        HashMap<String, Object> res = new HashMap<>();
        try {

            bollowService.save(deviceBorrow);
            res.put("code", "200");
            res.put("msg", "申请成功!");

        }catch (Exception e){
            res.put("code", "500");
            res.put("msg", "申请失败, 请联系管理员!");
        }
        return res;
    }

    //设备转借审批记录
    @RequestMapping(value = "/bollow/check", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllBollowCheck(@RequestParam int page, @RequestParam int limit, HttpSession session){
        Object id = session.getAttribute("id");
        Integer user_id = Integer.valueOf(String.valueOf(id));
        List<DeviceBorrow> deviceBorrows = bollowService.findAllByUserId(user_id);

        int begin = (page-1)*limit;
        int end = limit;

        String count = String.valueOf(deviceBorrows.size());
        List<DeviceBorrow> data = bollowService.findAllByPage(begin, end);

        JsonResult<List<DeviceBorrow>> jsonResult = new JsonResult(data);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }


    @RequestMapping(value = "/bollow/check", method = RequestMethod.POST)
    @ResponseBody
    public Object checkUp(@RequestParam Integer bollowid,
                          @RequestParam Integer deviceid,
                          @RequestParam String result,
                          HttpSession session){

        Object id = session.getAttribute("id");
        String username = session.getAttribute("username").toString();
        Integer user_id = Integer.valueOf(String.valueOf(id));


        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "操作成功!");
        try{
            Device device = deviceService.findOneById(deviceid);
            DeviceBorrow deviceBorrow = bollowService.findOneById(bollowid);

            //更新device bollow表
            deviceBorrow.setAuthorize_id(user_id);
            deviceBorrow.setAuthorize_name(username);
            Calendar calendar = Calendar.getInstance();
            deviceBorrow.setStart_time(calendar.getTime()); //审核时间

            if(result.equals("同意")){
                if(device.getStatus().equals("在库")){
                    //可以转借
                    //更新device的status为借出
                    device.setStatus("借出");
                }else{
                    result = "拒绝";
                    res.put("msg", "该设备无法借出, 可能该设备不在库中。请联系管理员!");
                }
            }
            deviceBorrow.setAuthorize_result(result);

            //更新数据库
            deviceService.save(device);
            bollowService.save(deviceBorrow);
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", e.toString());
        }
        return res;

    }





}
