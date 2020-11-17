package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Musician;
import java.util.ArrayList;

public interface MusicianMapper {

    @Select("SELECT * from Musician")
    ArrayList<Musician> select();

    @Insert("INSERT INTO Music VALUES (#{musicId},#{musicianId}, #{musicName},#{description},"+
    "#{styleId}, #{instrumentId}, #{sceneId}, #{length},now(),#{price},#{fileUrl})")
    int insert(@Param("musicId") Integer musicId,
    @Param("musicianId") Integer musicianId,
    @Param("musicName") String musicName, 
    @Param("description") String description, 
    @Param("styleId") Integer styleId,
    @Param("instrumentId") Integer instrumentId,
    @Param("sceneId") Integer sceneId,
    @Param("length") Integer length,
    @Param("price") Integer price,
    @Param("fileUrl") String fileUrl);

    @Select("SELECT * from Musician where musicianId = #{musicianId}")
    Musician selectByID(@Param("musicianId")  Integer id);
    
    @Delete("Delete from Music where musicianId = #{musicianId}")
    int deleteByID(@Param("musicianId")  Integer id);  


}
