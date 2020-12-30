package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.AcceptTask;
import java.util.ArrayList;

public interface AcceptTaskMapper {

    @Select("SELECT * from AcceptTask")
    ArrayList<AcceptTask> select();

    @Insert("INSERT INTO AcceptTask VALUES (#{taskId},#{musicianId}, #{status},"+
    "now(),null,null)")
    int insert(@Param("taskId") Integer taskId,
    @Param("musicianId") Integer musicianId,
    @Param("status") Integer status);

    @Update("Update AcceptTask SET status = #{status} ,musicId = #{musicId},"+ 
    "finishTime = now() where taskId = #{taskId}")
    int updateStatusByID(@Param("taskId")  Integer id,@Param("status")Integer status,
    @Param("musicId")Integer musicId);  

    @Select("SELECT * from AcceptTask where taskId = #{taskId}")
    AcceptTask selectByID(@Param("taskId")  Integer id);
    
    @Delete("Delete from AcceptTask where taskId = #{taskId}")
    int deleteByID(@Param("taskId")  Integer id);  


}
