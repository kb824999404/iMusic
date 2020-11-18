package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.MusicianMapper;
import com.sse.iMusic.Models.Musician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MusicianService {
    @Autowired
    private MusicianMapper musicianMapper;

    public Musician getMusicianByID(int musicianId)
    {
        return musicianMapper.selectByID(musicianId);
    }

    public ArrayList<Musician> getAllMusician()
    {
        return musicianMapper.select();
    }

    public int addMusician(Integer musicianId,Integer userId,
    String musicianName,String description,
    String country,Integer styleId,Integer instrumentId)
    {
        return musicianMapper.insert(musicianId,userId,
        musicianName,description,
        country,styleId,instrumentId);
    }



    public int deleteMusician(int musicianId)
    {
        return musicianMapper.deleteByID(musicianId);
    }


}
