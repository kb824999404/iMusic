package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.StarMusic;
import java.util.ArrayList;

public interface StarMusicMapper {

    @Select("SELECT * from StarMusic")
    ArrayList<StarMusic> select();

    @Insert("INSERT INTO StarMusic VALUES (#{musicId},#{userId},now())")
    int insert(@Param("musicId") Integer musicId,
    @Param("userId") Integer userId);

    @Select("SELECT * from StarMusic where musicId = #{musicId}")
    ArrayList<StarMusic> selectByMusicID(@Param("musicId")  Integer musicId);

    @Select("SELECT * from StarMusic where userId = #{userId}")
    ArrayList<StarMusic> selectByUserID(@Param("userId")  Integer userId);

    @Select("SELECT * from StarMusic where userId = #{userId} and musicId = #{musicId}")
    StarMusic selectByTwoID(@Param("userId")  Integer userId,@Param("musicId")  Integer musicId);
    
    @Delete("Delete from StarMusic where musicId = #{musicId} and userId=#{userId}")
    int delete(@Param("musicId")  Integer musicId,@Param("userId") Integer userId);  


}
