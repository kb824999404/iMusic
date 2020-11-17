package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;

import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Models.Musician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/home")
class HomeController {

    @PostMapping("/getRecommendMusic")
    @ResponseBody
    public Map<String, Object> getRecommendMusic(@RequestParam("userId") int userId){
        Music music=new Music("Music","This is a music!");
        ArrayList<Music> musicList=new ArrayList<Music>();
        musicList.add(music);

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("recommendMusic",musicList);

        return result;
    }

    @PostMapping("/getRecommendMusician")
    @ResponseBody
    public Map<String, Object> getRecommendMusician(@RequestParam("userId") int userId){
        ArrayList<Musician> musicianList=new ArrayList<Musician>();

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("recommendMusician",musicianList);

        return result;
    }

    @PostMapping("/getNewMusic")
    @ResponseBody
    public Map<String, Object> getNewMusic(@RequestParam("userId") int userId){
        Music music=new Music("Music","This is a music!");
        ArrayList<Music> musicList=new ArrayList<Music>();
        musicList.add(music);

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("newMusic",musicList);

        return result;
    }


}