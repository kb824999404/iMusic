package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.MusicMapper;
import com.sse.iMusic.Models.Music;
import com.sse.iMusic.Mappers.MusicianMapper;
import com.sse.iMusic.Models.Musician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MusicService {
    @Autowired
    private MusicMapper musicMapper;
    @Autowired
    private MusicianMapper musicianMapper;

    public Music getMusicByID(int musicId)
    {
        Music music=musicMapper.selectByID(musicId);
        music.musicianName=musicianMapper.selectByID(music.musicianId).musicianName;
        return music;
    }

    public ArrayList<Music> getMusicMusicianName(ArrayList<Music> musics)
    {
        for(Music music:musics){
            music.musicianName=musicianMapper.selectByID(music.musicianId).musicianName;
        }
        return musics;
    }

    public ArrayList<Music> getAllMusic()
    {
        return getMusicMusicianName(musicMapper.select());
    }



    public ArrayList<Music> getAllCheckedMusic()
    {
        return getMusicMusicianName(musicMapper.selectAllChecked());
    }

    public ArrayList<Music> getAllMusicSortByTime()
    {
        ArrayList<Music> musics=getMusicMusicianName(musicMapper.select());
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

    public int reviewMusic(int musicId,int checked)
    {
        return musicMapper.updateCheckdByID(musicId,checked);
    }


}
