package com.sse.iMusic.Models;

public class Music {
    public Integer musicId;
    public Integer musicianId;
    public String musicName;
    public String musicianName;
    public String description;
    public Integer styleId;
    public Integer instrumentId;
    public Integer sceneId;
    public Integer length;
    public String publishTime;
    public Integer price;
    public String fileUrl;
    public Integer checked;
    public Music(String _musicName,String _description){
        this.musicName=_musicName;
        this.description=_description;
    }

}