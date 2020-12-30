package com.sse.iMusic.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import com.sse.iMusic.Models.Instrument;
import java.util.ArrayList;

public interface InstrumentMapper {

    @Select("SELECT * from Instrument")
    ArrayList<Instrument> select();

    @Insert("INSERT INTO Instrument VALUES (#{instrumentId},#{instrumentName}")
    int insert(@Param("instrumentId") Integer instrumentId,@Param("instrumentName") String instrumentName);

    @Select("SELECT * from Instrument where instrumentId=#{instrumentId}")
    Instrument selectByID(@Param("instrumentId") Integer instrumentId);

}
