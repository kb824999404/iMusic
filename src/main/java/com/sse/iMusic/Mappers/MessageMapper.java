package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Message;
import java.util.ArrayList;

public interface MessageMapper {

    @Select("SELECT * from Message")
    ArrayList<Message> select();

    @Insert("INSERT INTO Message VALUES (#{senderId},#{receiverId},now(),#{content})")
    int insert(@Param("senderId") Integer senderId,
    @Param("receiverId") Integer receiverId,
    @Param("content") String content);

    @Select("SELECT * from Message where senderId = #{senderId} and receiverId=#{receiverId}")
    ArrayList<Message> selectByID(@Param("senderId") Integer senderId,
    @Param("receiverId") Integer receiverId);
    

}
