package com.sse.iMusic.Controllers;

import org.springframework.util.ResourceUtils;
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

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/music")
class MusicController {

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

    @PostMapping("/getAllMusic")
    @ResponseBody
    public Map<String, Object> getAllMusic(@RequestParam("styleId") int styleId,
        @RequestParam("instrumentId") int instrumentId,
        @RequestParam("sceneId") int sceneId,
        @RequestParam("length") int length,
        @RequestParam("searchKey") ArrayList<String> searchKey){

        Map<String, Object> result = new HashMap<>();
        ArrayList<Music> musics=musicService.getAllCheckedMusic();
        ArrayList<Music> resultMusics=new ArrayList<Music>();

        double[] minLens={0,0.5,1,2,3};
        double[] maxLens={0.5,1,2,3,100};
        int minLen=0,maxLen=0;
        if(length!=-1){
            minLen=(int)(minLens[length]*60);
            maxLen=(int)(maxLens[length]*60);
        }

        
        for(Music music:musics)
        {
            if(styleId!=-1&&music.styleId!=styleId)
                continue;
            if(instrumentId!=-1&&music.instrumentId!=instrumentId)
                continue;
            if(sceneId!=-1&&music.sceneId!=sceneId)
                continue;
            if(length!=-1&&!(music.length>minLen&&music.length<=maxLen))
                continue;
            if(searchKey.size()>0){
                boolean hasKey=false;
                for(String key:searchKey){
                    if(music.musicName.contains(key)||music.description.contains(key)){
                        hasKey=true;
                        break;
                    }
                }
                if(!hasKey) continue;
            }
            
            resultMusics.add(music);
        }

        result.put("status","true");
        result.put("musics",resultMusics);
        result.put("min",minLen);
        result.put("max",maxLen);


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

    @PostMapping("/starMusic")
    @ResponseBody
    public Map<String, Object> starMusic(@RequestParam("musicId") int musicId,
        @RequestParam("userId") int userId){

        Map<String, Object> result = new HashMap<>();
        int resultCode=starMusicService.addStarMusic(musicId,userId);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","收藏音乐失败！");
        }

        return result;
    }

    @PostMapping("/purchaseMusic")
    @ResponseBody
    public Map<String, Object> purchaseMusic(@RequestParam("musicId") int musicId,
        @RequestParam("userId") int userId,
        @RequestParam("payedMoney") int payedMoney){

        Map<String, Object> result = new HashMap<>();
        int resultCode=purchaseMusicService.addPurchaseMusic(musicId,userId,payedMoney);

        if(resultCode==1){
            result.put("status","true");
        }
        else{
            result.put("status","false");
            result.put("message","购买音乐失败！");
        }

        return result;
    }

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

        Musician musician=musicianService.getMusicianByID(musicianId);
        Style style=styleService.getStyleByID(styleId);
        Instrument instrument=instrumentService.getInstrumentByID(instrumentId);
        Scene scene=sceneService.getSceneByID(sceneId);

        if(musician==null)
        {
            result.put("status","false");
            result.put("message","该音乐人不存在！");
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
            instrumentId,sceneId,length,price,fileUrl);
    
            if(resultCode==1){
                result.put("status","true");
            }
            else{
                result.put("status","false");
                result.put("message","发布音乐失败！");
            }
        }


        return result;
    }

    @PostMapping("/uploadMusic")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {

        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("status","false");
            result.put("message","找不到上传的文件！");
            return result;
        }

        String fileName = file.getOriginalFilename();

        



        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            System.out.println(path.getAbsolutePath());
            File upload = new File(path.getAbsolutePath(), "static/uploadMusic/");
            if (!upload.exists()) upload.mkdirs();
            String uploadPath = upload + "\\";
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");


            file.transferTo(new File( uploadPath+uuid+fileName));
            result.put("status","true");
            result.put("fileUrl",uuid+fileName);
            return result;
        } catch (IOException e) {
            
        }
        result.put("status","false");
        result.put("message","上传失败！");
        return result;
    }


}