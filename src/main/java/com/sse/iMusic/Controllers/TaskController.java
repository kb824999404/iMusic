package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Task;
import com.sse.iMusic.Service.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/task")
class TaskController {

    @Autowired
    private TaskService taskService;

    // TODO
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
        Task task=taskService.getTaskByID(taskId);
        if(task!=null)
        {
            result.put("status","true");
            result.put("taskInfo",task);
        }
        else
        {
            result.put("status","false");
            result.put("message","该任务不存在！");

        }

        return result;
    }

    // TODO:判断用户是否存在
    @PostMapping("/getPublishTask")
    @ResponseBody
    public Map<String, Object> getPublishTask(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Task> tasks=taskService.getAllTask();
        ArrayList<Task> publishTasks=new ArrayList<Task>();

        for(Task task : tasks){
            if(task.userId==userId){
                publishTasks.add(task);
            }
        }

        result.put("status","true");
        result.put("publishTask",publishTasks);

        return result;
    }

    // TODO
    @PostMapping("/getAcceptTask")
    @ResponseBody
    public Map<String, Object> getAcceptTask(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        result.put("status","true");
        result.put("musicianId",musicianId);

        return result;
    }

}