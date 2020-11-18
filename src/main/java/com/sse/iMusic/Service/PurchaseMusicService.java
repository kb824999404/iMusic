package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.PurchaseMusicMapper;
import com.sse.iMusic.Models.PurchaseMusic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class PurchaseMusicService {
    @Autowired
    private PurchaseMusicMapper purchaseMusicMapper;

    public ArrayList<PurchaseMusic> getPurchaseMusicByMusicID(int musicId)
    {
        return purchaseMusicMapper.selectByMusicID(musicId);
    }

    public ArrayList<PurchaseMusic> getPurchaseMusicByUserID(int userId)
    {
        return purchaseMusicMapper.selectByUserID(userId);
    }


    public int addPurchaseMusic(Integer musicId,Integer userId,Integer payedMoney)
    {
        return purchaseMusicMapper.insert(musicId,userId,payedMoney);
    }

    public int deletePurchaseMusic(Integer musicId,Integer userId)
    {
        return purchaseMusicMapper.delete(musicId,userId);
    }


}