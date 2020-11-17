package com.sse.iMusic.Models;

public class Music {
    public int musicId;
    public int musicianId;
    public String musicName;
    public String description;
    public int styleId;
    public int instrumentId;
    public int sceneId;
    public int length;
    public String publishTime;
    public int price;
    public String fileUrl;
    public Music(String _musicName,String _description){
        this.musicName=_musicName;
        this.description=_description;
    }

}