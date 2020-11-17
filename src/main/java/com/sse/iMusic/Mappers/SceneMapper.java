package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Scene;
import java.util.ArrayList;

public interface SceneMapper {

    @Select("SELECT * from Scene")
    ArrayList<Scene> select();

    @Insert("INSERT INTO Scene VALUES (#{sceneId},#{sceneName}")
    int insert(@Param("sceneId") Integer sceneId,@Param("sceneName") String sceneName);

    @Select("SELECT * from Scene where styleId=#{sceneId}")
    Scene selectByID(@Param("sceneId") Integer sceneId);

}
