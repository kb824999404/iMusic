package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;

import com.sse.iMusic.Models.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/music")
class MusicController {

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
        result.put("status","true");


        return result;
    }

    @PostMapping("/getAllInstrument")
    @ResponseBody
    public Map<String, Object> getAllInstrument(){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");


        return result;
    }

    @PostMapping("/getAllScene")
    @ResponseBody
    public Map<String, Object> getAllScene(){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");


        return result;
    }

    @PostMapping("/getMusicInfo")
    @ResponseBody
    public Map<String, Object> getMusicInfo(@RequestParam("musicId") int musicId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicId",musicId);


        return result;
    }

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

    @PostMapping("/publishMusic")
    @ResponseBody
    public Map<String, Object> publishMusic(@RequestParam("musicName") String musicName){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicName",musicName);


        return result;
    }


}