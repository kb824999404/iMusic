package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping("/music")
class MusicController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;

    // TODO
    @PostMapping("/getAllMusic")
    @ResponseBody
    public Map<String, Object> getAllMusic(@RequestParam("styleId") int styleId,
        @RequestParam("instrumentId") int instrumentId,
        @RequestParam("sceneId") int sceneId,
        @RequestParam("length") int length,
        @RequestParam("price") int price){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("styleId",styleId);
        result.put("instrumentId",instrumentId);
        result.put("sceneId",sceneId);
        result.put("length",length);
        result.put("price",price);

        return result;
    }

    @PostMapping("/getAllStyle")
    @ResponseBody
    public Map<String, Object> getAllStyle(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Style> styles=styleService.getAllStyle();
        result.put("status","true");
        result.put("styles",styles);

        return result;
    }


    @PostMapping("/getAllInstrument")
    @ResponseBody
    public Map<String, Object> getAllInstrument(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Instrument> instruments=instrumentService.getAllInstrument();
        result.put("status","true");
        result.put("instruments",instruments);


        return result;
    }

    @PostMapping("/getAllScene")
    @ResponseBody
    public Map<String, Object> getAllScene(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Scene> scenes=sceneService.getAllScene();
        result.put("status","true");
        result.put("scenes",scenes);


        return result;
    }

    @PostMapping("/getMusicInfo")
    @ResponseBody
    public Map<String, Object> getMusicInfo(@RequestParam("musicId") int musicId){

        Map<String, Object> result = new HashMap<>();
        Music music=musicService.getMusicByID(musicId);
        if(music!=null)
        {
            result.put("status","true");
            result.put("musicInfo",music);
        }
        else
        {
            result.put("status","false");
            result.put("message","该音乐不存在！");

        }


        return result;
    }

    // TODO
    @PostMapping("/purchaseMusic")
    @ResponseBody
    public Map<String, Object> purchaseMusic(@RequestParam("musicId") int musicId,
        @RequestParam("userId") int userId,
        @RequestParam("payedMoney") int payedMoney){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicId",musicId);
        result.put("userId",userId);
        result.put("payedMoney",payedMoney);

        return result;
    }

    // TODO:判断各个ID是否存在
    @PostMapping("/publishMusic")
    @ResponseBody
    public Map<String, Object> publishMusic(@RequestParam("musicianId") int musicianId,
    @RequestParam("musicName") String musicName, 
    @RequestParam("description") String description,
    @RequestParam("styleId") int styleId,
    @RequestParam("instrumentId") int instrumentId,
    @RequestParam("sceneId") int sceneId,
    @RequestParam("length") int length,
    @RequestParam("price") int price,
    @RequestParam("fileUrl") String fileUrl){

        Map<String, Object> result = new HashMap<>();

        ArrayList<Music> musics=musicService.getAllMusic();
        int musicId=musics.size()+1;

        int resultCode=musicService.addMusic(musicId,musicianId,musicName,description,styleId,
        instrumentId,sceneId,length,price,fileUrl);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","发布音乐失败！");
        }

        return result;
    }


}