package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/musician")
class MusicianController {

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
        result.put("status","true");
        result.put("musicianId",musicianId);


        return result;
    }

    @PostMapping("/getPublishMusic")
    @ResponseBody
    public Map<String, Object> getPublishMusic(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicianId",musicianId);

        return result;
    }

    @PostMapping("/applyMusician")
    @ResponseBody
    public Map<String, Object> applyMusician(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }
}