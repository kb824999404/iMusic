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

    @Select("SELECT * from Musician where checked = 1")
    ArrayList<Musician> selectAllChecked();

    @Insert("INSERT INTO Musician VALUES (#{musicianId},#{userId}, #{musicianName},#{description},"+
    "#{countryId},#{styleId}, #{instrumentId},0)")
    int insert(@Param("musicianId") Integer musicianId,
    @Param("userId") Integer userId,
    @Param("musicianName") String musicianName, 
    @Param("description") String description,
    @Param("countryId") Integer country,
    @Param("styleId") Integer styleId,
    @Param("instrumentId") Integer instrumentId);

    @Select("SELECT * from Musician where musicianId = #{musicianId}")
    Musician selectByID(@Param("musicianId")  Integer id);
    
    @Delete("Delete from Musician where musicianId = #{musicianId}")
    int deleteByID(@Param("musicianId")  Integer id);  

    @Update("Update Musician SET checked = #{checked} where musicianId = #{musicianId}")
    int updateCheckdByID(@Param("musicianId")  Integer id,@Param("checked")Integer checked);  
}
