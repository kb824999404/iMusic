package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Country;
import java.util.ArrayList;

public interface CountryMapper {

    @Select("SELECT * from Country")
    ArrayList<Country> select();

    @Insert("INSERT INTO Country VALUES (#{countryId},#{countryName}")
    int insert(@Param("countryId") Integer countryId,@Param("countryName") String countryName);

    @Select("SELECT * from Country where countryId=#{countryId}")
    Country selectByID(@Param("countryId") Integer countryId);

}
