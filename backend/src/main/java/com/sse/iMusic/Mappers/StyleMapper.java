package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Style;
import java.util.ArrayList;

public interface StyleMapper {

    @Select("SELECT * from Style")
    ArrayList<Style> select();

    @Insert("INSERT INTO Style VALUES (#{styleId},#{styleName}")
    int insert(@Param("styleId") Integer styleId,@Param("styleName") String styleName);

    @Select("SELECT * from Style where styleId=#{styleId}")
    Style selectByID(@Param("styleId") Integer styleId);

}
