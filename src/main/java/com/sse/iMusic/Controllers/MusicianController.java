package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/musician")
class MusicianController {

    @Autowired
    private MusicianService musicianService;
    @Autowired
    private MusicService musicService;

    // TODO
    @PostMapping("/getAllMusician")
    @ResponseBody
    public Map<String, Object> getAllMusician(){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");


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

    //TODO:判断音乐人是否存在
    @PostMapping("/getPublishMusic")
    @ResponseBody
    public Map<String, Object> getPublishMusic(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();

        ArrayList<Music> musics=musicService.getAllMusic();
        ArrayList<Music> publishMusics=new ArrayList<Music>();

        for(Music music : musics){
            if(music.musicianId==musicianId){
                publishMusics.add(music);
            }
        }

        result.put("status","true");
        result.put("publishMusic",publishMusics);

        return result;
    }

    // TODO
    @PostMapping("/applyMusician")
    @ResponseBody
    public Map<String, Object> applyMusician(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }
}