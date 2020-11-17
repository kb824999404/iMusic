package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/task")
class TaskController {

    @PostMapping("/publishTask")
    @ResponseBody
    public Map<String, Object> publishTask(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }

    @PostMapping("/getTaskInfo")
    @ResponseBody
    public Map<String, Object> getTaskInfo(@RequestParam("taskId") int taskId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("taskId",taskId);

        return result;
    }

    @PostMapping("/getPublishTask")
    @ResponseBody
    public Map<String, Object> getPublishTask(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("userId",userId);

        return result;
    }

    @PostMapping("/getAcceptTask")
    @ResponseBody
    public Map<String, Object> getAcceptTask(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicianId",musicianId);

        return result;
    }

}