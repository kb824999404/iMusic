package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.User;
import java.util.ArrayList;

public interface UserMapper {

    @Select("SELECT * from User")
    ArrayList<User> select();

    @Insert("INSERT INTO User VALUES (#{id}, #{name}, #{pwd}, #{avatar}, #{type})")
    int insert(@Param("id") Integer id,@Param("name") String name, @Param("pwd") String pwd,
    @Param("avatar") String avatar,@Param("type") Integer type);

    @Select("SELECT * from User where userId = #{userId}")
    User selectByID(@Param("userId")  Integer id);
    
    @Delete("Delete from User where userId = #{userId}")
    int deleteByID(@Param("userId")  Integer id);  


}
