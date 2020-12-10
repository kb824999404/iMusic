package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Service.MusicianService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/home")
class HomeController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicianService musicianService;

    // TODO:添加推荐功能
    @PostMapping("/getRecommendMusic")
    @ResponseBody
    public Map<String, Object> getRecommendMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Music> musics=musicService.getAllMusic();

        result.put("status","true");
        result.put("recommendMusic",musics);

        return result;
    }

    // TODO:添加推荐功能
    @PostMapping("/getRecommendMusician")
    @ResponseBody
    public Map<String, Object> getRecommendMusician(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Musician> musicians=musicianService.getAllMusician();

        result.put("status","true");
        result.put("recommendMusician",musicians);

        return result;
    }

    @PostMapping("/getNewMusic")
    @ResponseBody
    public Map<String, Object> getNewMusic(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Music> musics=musicService.getAllMusicSortByTime();

        result.put("status","true");
        result.put("newMusic",musics);

        return result;
    }


}