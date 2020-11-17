package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/account")
class AccountController {

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("userId") int userId,
        @RequestParam("password") String password){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);
        result.put("password",password);


        return result;
    }

    @PostMapping("/regesiter")
    @ResponseBody
    public Map<String, Object> regesiter(@RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("userType") int userType){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("username",username);
        result.put("password",password);
        result.put("userType",userType);

        return result;
    }

    @PostMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }

    @PostMapping("/getStarMusic")
    @ResponseBody
    public Map<String, Object> getStarMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }

    @PostMapping("/getPurchasedMusic")
    @ResponseBody
    public Map<String, Object> getPurchasedMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }

}