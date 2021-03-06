package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.MusicianMapper;
import com.sse.iMusic.Models.Musician;
import com.sse.iMusic.Models.Music;
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

    public Musician getMusicianByUserID(int userId)
    {
        return musicianMapper.selectByUserID(userId);
    }

    public ArrayList<Musician> getAllMusician()
    {
        return musicianMapper.select();
    }


    public ArrayList<Musician> getAllCheckedMusician()
    {
        return musicianMapper.selectAllChecked();
    }

    public int addMusician(Integer musicianId,Integer userId,
    String musicianName,String description,
    Integer  countryId,Integer styleId,Integer instrumentId,String appendixUrl)
    {
        return musicianMapper.insert(musicianId,userId,
        musicianName,description,
        countryId,styleId,instrumentId,appendixUrl);
    }



    public int deleteMusician(int musicianId)
    {
        return musicianMapper.deleteByID(musicianId);
    }

    public int reviewMusician(int musicianId,int checked)
    {
        return musicianMapper.updateCheckdByID(musicianId,checked);
    }


}
