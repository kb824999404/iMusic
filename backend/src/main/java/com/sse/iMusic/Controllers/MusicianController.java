package com.sse.iMusic.Controllers;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import com.sse.iMusic.Models.Style;
import com.sse.iMusic.Service.StyleService;
import com.sse.iMusic.Models.Scene;
import com.sse.iMusic.Service.SceneService;
import com.sse.iMusic.Models.Instrument;
import com.sse.iMusic.Service.InstrumentService;
import com.sse.iMusic.Models.Country;
import com.sse.iMusic.Service.CountryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/musician")
class MusicianController {

    @Autowired
    private MusicianService musicianService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private UserService userService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private CountryService countryService;

    @PostMapping("/getAllMusician")
    @ResponseBody
    public Map<String, Object> getAllMusician(@RequestParam("countryId") int countryId,
    @RequestParam("styleId") int styleId,
    @RequestParam("instrumentId") int instrumentId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Musician> musicians=musicianService.getAllCheckedMusician();
        ArrayList<Musician> resultMusicians=new ArrayList<Musician>();


        for(Musician musician:musicians){
            if(styleId!=-1&&musician.favMusicStyle!=styleId)
            continue;
            if(instrumentId!=-1&&musician.favInstrument!=instrumentId)
            continue;
            if(countryId!=-1&&musician.countryId!=countryId)
            continue;
            resultMusicians.add(musician);
        }
        result.put("status","true");
        result.put("musicians",resultMusicians);


        return result;
    }

    @PostMapping("/getMusicianInfo")
    @ResponseBody
    public Map<String, Object> getMusicianInfo(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        Musician musician=musicianService.getMusicianByID(musicianId);
        if(musician!=null)
        {
            result.put("status","true");
            result.put("musicianInfo",musician);
        }
        else
        {
            result.put("status","false");
            result.put("message","该音乐家不存在！");

        }


        return result;
    }

    @PostMapping("/getPublishMusic")
    @ResponseBody
    public Map<String, Object> getPublishMusic(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();

        Musician musician=musicianService.getMusicianByID(musicianId);
        if(musician==null)
        {
            result.put("status","false");
            result.put("message","该音乐人不存在！");
        }
        else
        {
            ArrayList<Music> musics=musicService.getAllMusic();
            ArrayList<Music> publishMusics=new ArrayList<Music>();
    
            for(Music music : musics){
                if(music.musicianId==musicianId){
                    publishMusics.add(music);
                }
            }
    
            result.put("status","true");
            result.put("publishMusic",publishMusics);
        }


        return result;
    }

    @PostMapping("/applyMusician")
    @ResponseBody
    public Map<String, Object> applyMusician(@RequestParam("userId") Integer userId,
    @RequestParam("musicianName") String musicianName, 
    @RequestParam("description") String description,
    @RequestParam("countryId") Integer countryId,
    @RequestParam("favMusicStyle") Integer styleId,
    @RequestParam("favInstrument") Integer instrumentId,
    @RequestParam("appendixUrl") String appendixUrl
    ){

        Map<String, Object> result = new HashMap<>();
        Musician musician=musicianService.getMusicianByUserID(userId);
        if(musician!=null){
            result.put("status","false");
            result.put("message","已有该音乐人！");
            return result;
        }

        ArrayList<Musician> musicians=musicianService.getAllMusician();
        int musicianId;
        if(musicians.size()==0)
        {
            musicianId=1;
        }
        else
        {
            musicianId=musicians.get(musicians.size()-1).musicianId +1;
        }

        int resultCode=musicianService.addMusician(musicianId,userId,
        musicianName,description,
        countryId,styleId,instrumentId,appendixUrl);

        resultCode*=userService.modifyUserType(userId,2);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","申请音乐人认证失败！");
        }

        return result;
    }

    @PostMapping("/getAllCountry")
    @ResponseBody
    public Map<String, Object> getAllCountry(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Country> countrys=countryService.getAllCountry();
        result.put("status","true");
        result.put("countrys",countrys);

        return result;
    }

    @PostMapping("/uploadAppendix")
    @ResponseBody
    public Map<String, Object> uploadAppendix(@RequestParam("file") MultipartFile file) {

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
            File upload = new File(path.getAbsolutePath(), "static/musicianAppendix/");
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
}