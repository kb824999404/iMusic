package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.PurchaseMusic;
import java.util.ArrayList;

public interface PurchaseMusicMapper {

    @Select("SELECT * from PurchaseMusic")
    ArrayList<PurchaseMusic> select();

    @Insert("INSERT INTO PurchaseMusic VALUES (#{musicId},#{userId},now(),#{payedMoney})")
    int insert(@Param("musicId") Integer musicId,
    @Param("userId") Integer userId,
    @Param("payedMoney") Integer payedMoney);

    @Select("SELECT * from PurchaseMusic where musicId = #{musicId}")
    ArrayList<PurchaseMusic> selectByMusicID(@Param("musicId")  Integer musicId);

    @Select("SELECT * from PurchaseMusic where userId = #{userId}")
    ArrayList<PurchaseMusic> selectByUserID(@Param("userId")  Integer userId);

    @Select("SELECT * from PurchaseMusic where userId = #{userId} and musicId = #{musicId}")
    PurchaseMusic selectByTwoID(@Param("userId")  Integer userId,@Param("musicId")  Integer musicId);
    
    @Delete("Delete from PurchaseMusic where musicId = #{musicId} and userId=#{userId}")
    int delete(@Param("musicId")  Integer musicId,@Param("userId") Integer userId);  


}
