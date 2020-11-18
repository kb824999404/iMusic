package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import com.sse.iMusic.Models.StarMusic;
import com.sse.iMusic.Service.StarMusicService;
import com.sse.iMusic.Models.PurchaseMusic;
import com.sse.iMusic.Service.PurchaseMusicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private StarMusicService starMusicService;
    @Autowired
    private PurchaseMusicService purchaseMusicService;

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("userId") int userId,
        @RequestParam("password") String password){

        Map<String, Object> result = new HashMap<>();
        User user=userService.getUserByID(userId);
        if(user!=null)
        {
            if(user.password.equals(password)){
                result.put("status","true");
            }
            else{
                result.put("status","false");
                result.put("message","密码错误！");
            }
        }
        else
        {
            result.put("status","false");
            result.put("message","账号不存在！");

        }


        return result;
    }

    @PostMapping("/regesiter")
    @ResponseBody
    public Map<String, Object> regesiter(@RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("userType") int userType){

        Map<String, Object> result = new HashMap<>();

        ArrayList<User> users=userService.getAllUser();
        int userId=users.size()+1;
        int resultCode=userService.addUser(userId,username,password,"Default-Avatar",userType);
        if(resultCode==1){
            result.put("status","true");
            result.put("userId",userId);
        }
        else{
            result.put("status","false");
            result.put("message","注册失败！");
        }

        return result;
    }

    @PostMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        User user=userService.getUserByID(userId);
        if(user!=null)
        {
            result.put("status","true");
            result.put("userInfo",user);
        }
        else
        {
            result.put("status","false");
            result.put("message","账号不存在！");

        }

        return result;
    }

    @PostMapping("/getStarMusic")
    @ResponseBody
    public Map<String, Object> getStarMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        User user=userService.getUserByID(userId);
        if(user!=null)
        {
            ArrayList<StarMusic> starMusics=starMusicService.getStarMusicByUserID(userId);
            result.put("status","true");
            result.put("starMusics",starMusics);
        }
        else
        {
            result.put("status","false");
            result.put("message","账号不存在！");

        }

        return result;
    }

    @PostMapping("/getPurchaseMusic")
    @ResponseBody
    public Map<String, Object> getPurchasedMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        User user=userService.getUserByID(userId);
        if(user!=null)
        {
            ArrayList<PurchaseMusic> purchaseMusics=purchaseMusicService.getPurchaseMusicByUserID(userId);
            result.put("status","true");
            result.put("purchaseMusics",purchaseMusics);
        }
        else
        {
            result.put("status","false");
            result.put("message","账号不存在！");

        }

        return result;
    }

}