package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.StyleMapper;
import com.sse.iMusic.Models.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class StyleService {
    @Autowired
    private StyleMapper styleMapper;

    public Style getStyleByID(int styleId)
    {
        return styleMapper.selectByID(styleId);
    }

    public ArrayList<Style> getAllStyle()
    {
        return styleMapper.select();
    }


}
