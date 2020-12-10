package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Service.MusicService;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Service.MusicianService;
import com.sse.iMusic.Models.Style;
import com.sse.iMusic.Service.StyleService;
import com.sse.iMusic.Models.Scene;
import com.sse.iMusic.Service.SceneService;
import com.sse.iMusic.Models.Instrument;
import com.sse.iMusic.Service.InstrumentService;
import com.sse.iMusic.Models.StarMusic;
import com.sse.iMusic.Service.StarMusicService;
import com.sse.iMusic.Models.PurchaseMusic;
import com.sse.iMusic.Service.PurchaseMusicService;
import com.sse.iMusic.Models.Task;
import com.sse.iMusic.Service.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicianService musicianService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private StarMusicService starMusicService;
    @Autowired
    private PurchaseMusicService purchaseMusicService;
    @Autowired
    private TaskService taskService;

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

            result.put("status","true");
            result.put("tasks",tasks);
        }

        return result;
    }
}