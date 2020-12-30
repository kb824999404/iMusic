package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Task;
import com.sse.iMusic.Service.TaskService;
import com.sse.iMusic.Models.AcceptTask;
import com.sse.iMusic.Service.AcceptTaskService;
import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import com.sse.iMusic.Models.Style;
import com.sse.iMusic.Service.StyleService;
import com.sse.iMusic.Models.Scene;
import com.sse.iMusic.Service.SceneService;
import com.sse.iMusic.Models.Instrument;
import com.sse.iMusic.Service.InstrumentService;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;

import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/task")
class TaskController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private MusicianService musicianService;
    @Autowired
    private AcceptTaskService acceptTaskService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;


    @PostMapping("/publishTask")
    @ResponseBody
    public Map<String, Object> publishTask(@RequestParam("userId") Integer userId,
    @RequestParam("description") String description, 
    @RequestParam("styleId") Integer styleId,
    @RequestParam("instrumentId") Integer instrumentId,
    @RequestParam("sceneId") Integer sceneId,
    @RequestParam("length") Integer length){

        Map<String, Object> result = new HashMap<>();

        User user=userService.getUserByID(userId);
        Style style=styleService.getStyleByID(styleId);
        Instrument instrument=instrumentService.getInstrumentByID(instrumentId);
        Scene scene=sceneService.getSceneByID(sceneId);

        if(user==null)
        {
            result.put("status","false");
            result.put("message","该用户不存在！");
        }
        else if(style==null)
        {
            result.put("status","false");
            result.put("message","该风格不存在！");
        }
        else if(instrument==null)
        {
            result.put("status","false");
            result.put("message","该乐器不存在！"); 
        }
        else if(scene==null)
        {
            result.put("status","false");
            result.put("message","该场景不存在！");
        }
        else
        {
            ArrayList<Task> tasks=taskService.getAllTask();
            int taskId;

            if(tasks.size()==0)
            {
                taskId=1;
            }
            else
            {
                taskId=tasks.get(tasks.size()-1).taskId +1;
            }
    
            int resultCode=taskService.addTask(taskId,userId,description, 
            styleId,instrumentId,sceneId,length);
    
            if(resultCode==1){
                result.put("status","true");
            }
            else{
                result.put("status","false");
                result.put("message","发布任务失败！");
            }
        }


        return result;
    }


    @PostMapping("/acceptTask")
    @ResponseBody
    public Map<String, Object> acceptTask(@RequestParam("taskId") int taskId,
    @RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        Task task=taskService.getTaskByID(taskId);
        if(task!=null)
        {
            int resultCode=acceptTaskService.addTask(taskId,musicianId,0);
            if(resultCode==1){
                result.put("status","true");
            }
            else{
                result.put("status","false");
                result.put("message","接受任务失败！");
            }
        }
        else
        {
            result.put("status","false");
            result.put("message","该任务不存在！");

        }

        return result;
    }

    @PostMapping("/unacceptTask")
    @ResponseBody
    public Map<String, Object> unacceptTask(@RequestParam("taskId") int taskId){

        Map<String, Object> result = new HashMap<>();
        int resultCode=acceptTaskService.deleteTask(taskId);
        if(resultCode==1)
        {
            result.put("status","true");
        }
        else
        {
            result.put("status","false");
            result.put("message","取消任务失败！");

        }

        return result;
    }


    @PostMapping("/finishTask")
    @ResponseBody
    public Map<String, Object> finishTask(@RequestParam("taskId") int taskId,
    @RequestParam("musicianId") int musicianId,
    @RequestParam("musicName") String musicName, 
    @RequestParam("description") String description,
    @RequestParam("styleId") int styleId,
    @RequestParam("instrumentId") int instrumentId,
    @RequestParam("sceneId") int sceneId,
    @RequestParam("length") int length,
    @RequestParam("price") int price,
    @RequestParam("fileUrl") String fileUrl,
    @RequestParam("appendixUrl") String appendixUrl){

        Map<String, Object> result = new HashMap<>();

        ArrayList<Music> musics=musicService.getAllMusic();
        int musicId;
        if(musics.size()==0)
        {
            musicId=1;
        }
        else
        {
            musicId=musics.get(musics.size()-1).musicId +1;
        }

        int resultCode=musicService.addMusic(musicId,musicianId,musicName,description,styleId,
        instrumentId,sceneId,length,price,fileUrl,appendixUrl);

        resultCode*=acceptTaskService.finishTask(taskId, 1,musicId);


        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","完成任务失败！");
        }

        return result;
    }

    @PostMapping("/getTaskInfo")
    @ResponseBody
    public Map<String, Object> getTaskInfo(@RequestParam("taskId") int taskId){

        Map<String, Object> result = new HashMap<>();
        Task task=taskService.getTaskByID(taskId);
        Style style=styleService.getStyleByID(task.requiredStyleId);
        Instrument instrument=instrumentService.getInstrumentByID(task.requiredInstrumentId);
        Scene scene=sceneService.getSceneByID(task.requiredSceneId);
        User user=userService.getUserByID(task.userId);
        if(style!=null&&instrument!=null&&scene!=null){
            result.put("status","true");
            result.put("taskInfo",task);
            result.put("style",style);
            result.put("instrument",instrument);
            result.put("scene",scene);
            result.put("user",user);
        }
        else
        {
            result.put("status","false");
            result.put("message","该任务不存在！");

        }

        return result;
    }


    @PostMapping("/getPublishTask")
    @ResponseBody
    public Map<String, Object> getPublishTask(@RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();

        User user=userService.getUserByID(userId);

        if(user==null)
        {
            result.put("status","false");
            result.put("message","该用户不存在！");
        }
        else
        {

            ArrayList<Task> tasks=taskService.getAllTask();
            ArrayList<Task> publishTasks=new ArrayList<Task>();
    
            for(Task task : tasks){
                if(task.userId==userId){
                    publishTasks.add(task);
                }
            }
    
            result.put("status","true");
            result.put("publishTask",publishTasks);
        }


        return result;
    }

    @PostMapping("/getAcceptTask")
    @ResponseBody
    public Map<String, Object> getAcceptTask(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();

        Musician musician=musicianService.getMusicianByID(musicianId);

        if(musician==null)
        {
            result.put("status","false");
            result.put("message","该音乐人不存在！");
        }
        else
        {
            ArrayList<AcceptTask> tasks=acceptTaskService.getAllAcceptTask();
            ArrayList<AcceptTask> acceptTasks=new ArrayList<AcceptTask>();
    
            for(AcceptTask task : tasks){
                if(task.musicianId==musicianId){
                    acceptTasks.add(task);
                }
            }
    
            result.put("status","true");
            result.put("acceptTask",acceptTasks);
        }



        return result;
    }

    @PostMapping("/getAcceptStatus")
    @ResponseBody
    public Map<String, Object> getAcceptStatus(@RequestParam("taskId") int taskId){

        Map<String, Object> result = new HashMap<>();

        AcceptTask acceptTask=acceptTaskService.getAcceptTaskByID(taskId);
        if(acceptTask==null){
            result.put("status","true");
            result.put("isAccept","false");
        }
        else
        {
            result.put("status","true");
            result.put("isAccept","true");
            result.put("acceptTask", acceptTask);
        }


        return result;
    }

}