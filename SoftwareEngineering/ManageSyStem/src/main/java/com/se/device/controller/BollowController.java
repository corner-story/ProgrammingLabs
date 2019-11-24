package com.se.device.controller;



import com.se.device.entity.Device;
import com.se.device.entity.DeviceBorrow;
import com.se.device.entity.DeviceBroken;
import com.se.device.entity.DeviceFault;
import com.se.device.service.BollowService;
import com.se.device.service.BrokenService;
import com.se.device.service.DeviceService;
import com.se.device.service.FaultService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class BollowController {

    @Autowired
    private BollowService bollowService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FaultService faultService;

    @Autowired
    private BrokenService brokenService;

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



    //设备归还
    @RequestMapping(value = "/bollow/return", method = RequestMethod.POST)
    @ResponseBody
    public Object returnDevcie(@RequestParam Integer bollowid){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");

        try{

            DeviceBorrow deviceBorrow = bollowService.findOneById(bollowid);
            Device device = deviceService.findOneById(deviceBorrow.getDevice_id());
            Calendar calendar = Calendar.getInstance();
            //更新device_bollow 的 return_time
            deviceBorrow.setReturn_time(calendar.getTime());

            //更新设备信息 借出-->在库
            device.setStatus("在库");

            //更新到数据库
            bollowService.save(deviceBorrow);
            deviceService.save(device);
            res.put("msg", "归还成功!");
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", "归还失败, error: " + e.toString());
        }
        return res;
    }



    //获取所有的，没有归还的设备转借记录
    @RequestMapping(value = "/bollow/notback", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllNotBollows(@RequestParam int page, @RequestParam int limit, HttpSession session){
        Object id = session.getAttribute("id");
        Integer user_id = Integer.valueOf(String.valueOf(id));
        JsonResult<List<Object>> jsonResult = new JsonResult();
        try {
            List<DeviceBorrow> deviceBorrows = bollowService.findAllNotBackByUserId(user_id);

            int begin = (page-1)*limit;
            int end = limit;

            String count = String.valueOf(deviceBorrows.size());
            List<DeviceBorrow> data = bollowService.findAllNotBackByUserIdToPage(user_id, begin, end);
            List<Object> bollows = new ArrayList<>();
            //组装成新的数据
            for (DeviceBorrow datum : data) {
                HashMap<String, Object> bl = new HashMap<>();
                bl.put("id", datum.getId());
                bl.put("device_id", datum.getDevice_id());
                bl.put("device_name", datum.getDevice_name());
                bl.put("user_id", datum.getUser_id());
                bl.put("user_name", datum.getUser_name());
                bl.put("create_time", datum.getCreate_time());
                bl.put("do_what", datum.getDo_what());

                //获取当前对应的device
                Device device = deviceService.findOneById(datum.getDevice_id());
                String status = device.getStatus();
                bl.put("status", status);

                //根据不同的status在不同的表(broken, fault)中查找审批结果
                String authorize_id = "", authorize_name = "", authorize_result = "";
                if(status.equals("损坏")){
                    DeviceFault deviceFault = faultService.findOneByUserIdAndDeviceId(datum.getUser_id(), datum.getDevice_id());
                    authorize_id = String.valueOf(deviceFault.getAuthorize_id());
                    authorize_name = deviceFault.getAuthorize_name();
                    authorize_result = deviceFault.getAuthorize_result();
                }else if(status.equals("报废")){
                    DeviceBroken deviceBroken = brokenService.findOneByUserIdAndDeviceId(datum.getUser_id(), datum.getDevice_id());
                    authorize_id = String.valueOf(deviceBroken.getAuthorize_id());
                    authorize_name = deviceBroken.getAuthorize_name();
                    authorize_result = deviceBroken.getAuthorize_result();
                }

                bl.put("authorize_id", authorize_id);
                bl.put("authorize_name", authorize_name);
                bl.put("authorize_result", authorize_result);


                //添加到bollows中
                bollows.add(bl);

            }

            jsonResult.setData(bollows);
            jsonResult.setCount(count);
            jsonResult.setCode("0");

        }catch (Exception e){
            System.out.println(e);
            jsonResult.setMsg(e.toString());
        }
        return jsonResult;
    }



    //设备损坏
    @RequestMapping(value = "/bollow/fault", method = RequestMethod.POST)
    @ResponseBody
    public Object devcieFault(@RequestBody DeviceFault deviceFault){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");

        try{
            //首先保存该条记录
            faultService.save(deviceFault);
            //找到对应的device, 更改status
            Device device = deviceService.findOneById(deviceFault.getDevice_id());
            device.setStatus("损坏");
            deviceService.save(device);

            res.put("msg", "上报成功,请等待管理员审核!");
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", "上报失败, error:" + e.toString());
        }
        return res;
    }


    //设备损坏
    @RequestMapping(value = "/bollow/broken", method = RequestMethod.POST)
    @ResponseBody
    public Object devcieBroken(@RequestBody DeviceBroken deviceBroken){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");

        try{
            //首先保存该条记录
            brokenService.save(deviceBroken);
            Device device = deviceService.findOneById(deviceBroken.getDevice_id());
            device.setStatus("报废");
            deviceService.save(device);

            res.put("msg", "上报成功,请等待管理员审核!");
        }catch (Exception e){
            res.put("msg", "上报失败, error:" + e.toString());
            System.out.println(e);
        }
        return res;
    }


    //设备损坏核查
    @RequestMapping(value = "/fault/check", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllFaultCheck(@RequestParam int page, @RequestParam int limit, HttpSession session){
        List<DeviceFault> deviceFaults = faultService.findAll();

        int begin = (page-1)*limit;
        int end = limit;

        String count = String.valueOf(deviceFaults.size());
        List<DeviceFault> data = faultService.findAllToPage(begin, end);
        JsonResult<List<DeviceFault>> jsonResult = new JsonResult(data);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }


    //设备报废核查
    @RequestMapping(value = "/broken/check", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllBrokenCheck(@RequestParam int page, @RequestParam int limit, HttpSession session){
        List<DeviceBroken> deviceBrokens = brokenService.findAll();

        int begin = (page-1)*limit;
        int end = limit;

        String count = String.valueOf(deviceBrokens.size());
        List<DeviceBroken> data = brokenService.findAllToPage(begin, end);
        JsonResult<List<DeviceBroken>> jsonResult = new JsonResult(data);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }


    //对device fault和broken的状况进行核查
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Object checkUp(@RequestParam Integer id, @RequestParam String type, @RequestParam String result, HttpSession session){

        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");

        try{
            String username = session.getAttribute("username").toString();
            Integer user_id = Integer.valueOf(String.valueOf(session.getAttribute("id")));
            if(type.equals("损坏")){
                DeviceFault deviceFault = faultService.findOneById(id);
                deviceFault.setAuthorize_id(user_id);
                deviceFault.setAuthorize_name(username);
                deviceFault.setAuthorize_result(result);

                faultService.save(deviceFault);
            }else if(type.equals("报废")){
                DeviceBroken deviceBroken = brokenService.findOneById(id);
                deviceBroken.setAuthorize_id(user_id);
                deviceBroken.setAuthorize_name(username);
                deviceBroken.setAuthorize_result(result);
                brokenService.save(deviceBroken);
            }else{
                res.put("msg", "操作失败!");
            }
            res.put("msg", "操作成功!");
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", e.toString());
        }

        return res;
    }

}
