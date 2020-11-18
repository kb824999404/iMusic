package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.SceneMapper;
import com.sse.iMusic.Models.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class SceneService {
    @Autowired
    private SceneMapper sceneMapper;

    public Scene getSceneByID(int sceneId)
    {
        return sceneMapper.selectByID(sceneId);
    }

    public ArrayList<Scene> getAllScene()
    {
        return sceneMapper.select();
    }


}
