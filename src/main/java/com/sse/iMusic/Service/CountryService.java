package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.CountryMapper;
import com.sse.iMusic.Models.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class CountryService {
    @Autowired
    private CountryMapper countryMapper;

    public Country getCountryByID(int countryId)
    {
        return countryMapper.selectByID(countryId);
    }

    public ArrayList<Country> getAllCountry()
    {
        return countryMapper.select();
    }


}
