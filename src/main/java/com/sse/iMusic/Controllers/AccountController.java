package com.sse.iMusic.Controllers;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.StarMusic;
import com.sse.iMusic.Service.StarMusicService;
import com.sse.iMusic.Models.PurchaseMusic;
import com.sse.iMusic.Service.PurchaseMusicService;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicianService musicianService;
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

    @PostMapping("/getMyMusicianInfo")
    @ResponseBody
    public Map<String, Object> getMyMusicianInfo(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        Musician musician=musicianService.getMusicianByUserID(userId);
        if(musician!=null)
        {
            result.put("status","true");
            result.put("musicianInfo",musician);
        }
        else
        {
            result.put("status","false");
            result.put("message","音乐人不存在！");

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
            for(StarMusic starMusic:starMusics){
                Music music=musicService.getMusicByID(starMusic.musicId);
                starMusic.setMusicData(music);
            }
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
            for(PurchaseMusic purchaseMusic:purchaseMusics){
                Music music=musicService.getMusicByID(purchaseMusic.musicId);
                purchaseMusic.setMusicData(music);
            }
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

    @PostMapping("/uploadAvatar")
    @ResponseBody
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file) {

        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("status","false");
            result.put("message","找不到上传的文件！");
            return result;
        }

        String fileName = file.getOriginalFilename();

        



        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            System.out.println(path.getAbsolutePath());
            File upload = new File(path.getAbsolutePath(), "static/avatar/");
            if (!upload.exists()) upload.mkdirs();
            String uploadPath = upload + "/";
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");


            file.transferTo(new File( uploadPath+uuid+fileName));
            result.put("status","true");
            result.put("fileUrl",uuid+fileName);
            return result;
        } catch (IOException e) {
            
        }
        result.put("status","false");
        result.put("message","上传失败！");
        return result;
    }


    @PostMapping("/modifyAvatar")
    @ResponseBody
    public Map<String, Object> modifyAvatar(@RequestParam("userId") Integer userId,
        @RequestParam("avatar") String avatar){

        Map<String, Object> result = new HashMap<>();

        int resultCode=userService.modifyAvatar(userId, avatar);
        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","修改头像失败！");
        }

        return result;
    }

}