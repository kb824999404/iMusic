package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.InstrumentMapper;
import com.sse.iMusic.Models.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class InstrumentService {
    @Autowired
    private InstrumentMapper instrumentMapper;

    public Instrument getInstrumentByID(int instrumentId)
    {
        return instrumentMapper.selectByID(instrumentId);
    }

    public ArrayList<Instrument> getAllInstrument()
    {
        return instrumentMapper.select();
    }


}
