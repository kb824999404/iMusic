package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.UserMapper;
import com.sse.iMusic.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserByID(int userId)
    {
        return userMapper.selectByID(userId);
    }

    public ArrayList<User> getAllUser()
    {
        return userMapper.select();
    }

    public int addUser(Integer id,String name,String pwd,
    String avatar,Integer type)
    {
        return userMapper.insert(id,name,pwd,avatar,type);
    }

    public int deleteUser(int userId)
    {
        return userMapper.deleteByID(userId);
    }

    public int modifyUserType(int userId,int userType)
    {
        return userMapper.updateType(userId,userType);
    }

    
    public int modifyAvatar(int userId,String avatar)
    {
        return userMapper.updateAvatar(userId,avatar);
    }


}
