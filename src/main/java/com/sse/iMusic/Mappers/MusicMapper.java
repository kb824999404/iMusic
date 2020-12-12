package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Music;
import java.util.ArrayList;

public interface MusicMapper {

    @Select("SELECT * from Music")
    ArrayList<Music> select();

    @Select("SELECT * from Music where checked = 1")
    ArrayList<Music> selectAllChecked();

    @Insert("INSERT INTO Music VALUES (#{musicId},#{musicianId}, #{musicName},#{description},"+
    "#{styleId}, #{instrumentId}, #{sceneId}, #{length},now(),#{price},#{fileUrl},0)")
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

    @Select("SELECT * from Music where musicId = #{musicId}")
    Music selectByID(@Param("musicId")  Integer id);
    
    @Delete("Delete from Music where musicId = #{musicId}")
    int deleteByID(@Param("musicId")  Integer id);  

    @Update("Update Music SET checked = #{checked} where musicId = #{musicId}")
    int updateCheckdByID(@Param("musicId")  Integer id,@Param("checked")Integer checked);  
}
