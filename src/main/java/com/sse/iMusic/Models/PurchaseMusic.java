package com.sse.iMusic.Models;

public class PurchaseMusic {
    public Integer musicId;
    public Integer userId;
    public String purchaseTime;
    public Integer payedMoney;

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

    public void setMusicData(Music music){
        this.musicianId=music.musicianId;
        this.musicName=music.musicName;
        this.musicianName=music.musicianName;
        this.description=music.description;
        this.styleId=music.styleId;
        this.instrumentId=music.instrumentId;
        this.sceneId=music.sceneId;
        this.length=music.length;
        this.publishTime=music.publishTime;
        this.price=music.price;
        this.fileUrl=music.fileUrl;
    }

}