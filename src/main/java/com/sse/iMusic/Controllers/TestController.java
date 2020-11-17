package com.sse.iMusic.Controllers;

import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @PostMapping("/deleteuser")
    @ResponseBody
    public Map<String, Object> deleteUserTest(@RequestParam("userId") Integer id){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        int code=userService.deleteUser(id);
        result.put("result",code);
        return result;
    }

    @PostMapping("/adduser")
    @ResponseBody
    public Map<String, Object> addUserTest(@RequestParam("userId") Integer id,
    @RequestParam("userName") String name, 
    @RequestParam("password") String pwd,
    @RequestParam("avatar") String avatar,
    @RequestParam("userType") Integer type){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        int code=userService.addUser(id,name,pwd,avatar,type);
        result.put("result",code);
        return result;
    }
}
