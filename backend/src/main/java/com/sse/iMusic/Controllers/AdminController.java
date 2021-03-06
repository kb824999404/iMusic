package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;
import com.sse.iMusic.Models.Style;
import com.sse.iMusic.Service.StyleService;
import com.sse.iMusic.Models.Scene;
import com.sse.iMusic.Service.SceneService;
import com.sse.iMusic.Models.Instrument;
import com.sse.iMusic.Service.InstrumentService;
import com.sse.iMusic.Models.Task;
import com.sse.iMusic.Service.TaskService;
import com.sse.iMusic.Models.AcceptTask;
import com.sse.iMusic.Service.AcceptTaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private UserService userService;
    @Autowired
    private MusicianService musicianService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AcceptTaskService acceptTaskService;

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> Login(@RequestParam("password") String password){

        Map<String, Object> result = new HashMap<>();
        if(password.equals("123")){
            result.put("status","true");
        }
        else
        {
            result.put("status","false");
            result.put("message","密码错误！");
        }
        return result;
    }

    @PostMapping("/getAllMusic")
    @ResponseBody
    public Map<String, Object> getAllMusic(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Music> musics=musicService.getAllMusic();

        if(musics==null){
            result.put("status","false");
            result.put("message","获取所有音乐失败！");
        }
        else
        {

            result.put("status","true");
            result.put("musics",musics);
        }

        return result;
    }

    
    @PostMapping("/getAllMusician")
    @ResponseBody
    public Map<String, Object> getAllMusician(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Musician> musicians=musicianService.getAllMusician();

        if(musicians==null){
            result.put("status","false");
            result.put("message","获取所有音乐人失败！");
        }
        else
        {

            result.put("status","true");
            result.put("musicians",musicians);
        }

        return result;
    }

    @PostMapping("/getAllTask")
    @ResponseBody
    public Map<String, Object> getAllTask(){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Task> tasks=taskService.getAllTask();

        if(tasks==null){
            result.put("status","false");
            result.put("message","获取所有任务失败！");
        }
        else
        {
            for(Task task:tasks){
                AcceptTask acceptTask=acceptTaskService.getAcceptTaskByID(task.taskId);
                if(acceptTask==null){
                    task.status=-1;
                }
                else
                {
                    task.status=acceptTask.status;
                }
            }
            result.put("status","true");
            result.put("tasks",tasks);
        }

        return result;
    }

    @PostMapping("/deleteMusic")
    @ResponseBody
    public Map<String, Object> deleteMusic(@RequestParam("musicId") int musicId){

        Map<String, Object> result = new HashMap<>();
        int resultCode=musicService.deleteMusic(musicId);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","删除音乐失败！");
        }

        return result;
    }

    @PostMapping("/reviewMusic")
    @ResponseBody
    public Map<String, Object> reviewMusic(@RequestParam("musicId") int musicId,
    @RequestParam("checked") int checked){

        Map<String, Object> result = new HashMap<>();
        int resultCode=musicService.reviewMusic(musicId, checked);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","审核音乐失败！");
        }

        return result;
    }

    @PostMapping("/deleteMusician")
    @ResponseBody
    public Map<String, Object> deleteMusician(@RequestParam("musicianId") int musicianId){

        Map<String, Object> result = new HashMap<>();
        Musician musician=musicianService.getMusicianByID(musicianId);

        int resultCode=musicianService.deleteMusician(musicianId);
        resultCode*=userService.modifyUserType(musician.userId,1);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","删除音乐人失败！");
        }

        return result;
    }

    
    @PostMapping("/reviewMusician")
    @ResponseBody
    public Map<String, Object> reviewMusician(@RequestParam("musicianId") int musicianId,
    @RequestParam("checked") int checked){

        Map<String, Object> result = new HashMap<>();
        Musician musician=musicianService.getMusicianByID(musicianId);

        int resultCode=musicianService.reviewMusician(musicianId, checked);
        resultCode*=userService.modifyUserType(musician.userId,checked+2);


        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","认证音乐人失败！");
        }

        return result;
    }

    @PostMapping("/deleteTask")
    @ResponseBody
    public Map<String, Object> deleteTask(@RequestParam("taskId") int taskId){

        Map<String, Object> result = new HashMap<>();
        int resultCode=taskService.deleteTask(taskId);
        resultCode*=acceptTaskService.deleteTask(taskId);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","删除任务失败！");
        }

        return result;
    }
}