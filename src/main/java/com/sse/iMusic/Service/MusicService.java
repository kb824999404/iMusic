package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.MusicMapper;
import com.sse.iMusic.Models.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MusicService {
    @Autowired
    private MusicMapper musicMapper;

    public Music getMusicByID(int musicId)
    {
        return musicMapper.selectByID(musicId);
    }

    public ArrayList<Music> getAllMusic()
    {
        return musicMapper.select();
    }

    public ArrayList<Music> getAllMusicSortByTime()
    {
        ArrayList<Music> musics=musicMapper.select();
        Collections.sort(musics, new Comparator<Music>() {
            @Override
            public int compare(Music o1, Music o2) {
                int i = o1.publishTime.compareTo(o2.publishTime);
                if(i>0) return -1;
                else return 1;
            }
        });
        return musics;
    }

    public int addMusic(Integer musicId,Integer musicianId,
    String musicName,String description,Integer styleId,
    Integer instrumentId,Integer sceneId,
    Integer length,Integer price,String fileUrl)
    {
        return musicMapper.insert(musicId,musicianId,musicName,description,styleId,
        instrumentId,sceneId,length,price,fileUrl);
    }

    public int deleteMusic(int musicId)
    {
        return musicMapper.deleteByID(musicId);
    }


}
