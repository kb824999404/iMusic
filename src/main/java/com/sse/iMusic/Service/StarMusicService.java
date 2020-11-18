package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.StarMusicMapper;
import com.sse.iMusic.Models.StarMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class StarMusicService {
    @Autowired
    private StarMusicMapper starMusicMapper;

    public ArrayList<StarMusic> getStarMusicByMusicID(int musicId)
    {
        return starMusicMapper.selectByMusicID(musicId);
    }

    public ArrayList<StarMusic> getStarMusicByUserID(int userId)
    {
        return starMusicMapper.selectByUserID(userId);
    }


    public int addStarMusic(Integer musicId,Integer userId)
    {
        return starMusicMapper.insert(musicId,userId);
    }

    public int deleteStarMusic(Integer musicId,Integer userId)
    {
        return starMusicMapper.delete(musicId,userId);
    }


}
