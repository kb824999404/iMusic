package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Task;
import java.util.ArrayList;

public interface TaskMapper {

    @Select("SELECT * from Task")
    ArrayList<Task> select();

    @Insert("INSERT INTO Task VALUES (#{taskId},#{userId}, #{taskType},#{description},"+
    "#{styleId}, #{instrumentId}, #{sceneId}, #{length},now())")
    int insert(@Param("taskId") Integer taskId,
    @Param("userId") Integer userId,
    @Param("taskType") Integer taskType,
    @Param("description") String description, 
    @Param("styleId") Integer styleId,
    @Param("instrumentId") Integer instrumentId,
    @Param("sceneId") Integer sceneId,
    @Param("length") Integer length);

    @Select("SELECT * from Task where taskId = #{taskId}")
    Task selectByID(@Param("taskId")  Integer id);
    
    @Delete("Delete from Task where taskId = #{taskId}")
    int deleteByID(@Param("taskId")  Integer id);  


}
