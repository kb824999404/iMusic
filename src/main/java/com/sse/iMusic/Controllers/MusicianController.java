package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Models.Style;
import com.sse.iMusic.Service.StyleService;
import com.sse.iMusic.Models.Scene;
import com.sse.iMusic.Service.SceneService;
import com.sse.iMusic.Models.Instrument;
import com.sse.iMusic.Service.InstrumentService;
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
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;

    @PostMapping("/getAllMusician")
    @ResponseBody
    public Map<String, Object> getAllMusician(@RequestParam("country") String country,
    @RequestParam("styleId") int styleId,
    @RequestParam("instrumentId") int instrumentId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Musician> musicians=musicianService.getAllMusician();
        ArrayList<Musician> resultMusicians=new ArrayList<Musician>();
        Style style=styleService.getStyleByID(styleId);
        Instrument instrument=instrumentService.getInstrumentByID(instrumentId);

        if(style==null)
        {
            result.put("status","false");
            result.put("message","该风格不存在！");
        }
        else if(instrument==null)
        {
            result.put("status","false");
            result.put("message","该乐器不存在！"); 
        }
        else
        {
            for(Musician musician:musicians){
                if(musician.favMusicStyle==styleId&&
                musician.favInstrument==instrumentId&&
                musician.country.equals(country)){
                    resultMusicians.add(musician);
                }
            }
            result.put("status","true");
            result.put("musicians",resultMusicians);
        }


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
    @RequestParam("country") String country,
    @RequestParam("favMusicStyle") Integer styleId,
    @RequestParam("favInstrument") Integer instrumentId
    ){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Musician> musicians=musicianService.getAllMusician();
        int musicianId=musicians.size()+1;

        int resultCode=musicianService.addMusician(musicianId,userId,
        musicianName,description,
        country,styleId,instrumentId);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","申请音乐人认证失败！");
        }

        return result;
    }
}