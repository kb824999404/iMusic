package com.sse.iMusic.Models;

import java.io.Serializable;

public class User implements Serializable {
    public Integer userId;
    public String username;
    public String password;
    public String avatar;
    public Integer userType;

    public User(Integer _id,String _name,String _pwd,
    String _avatar,Integer _type)
    {
        userId=_id;
        username=_name;
        password=_pwd;
        avatar=_avatar;
        userType=_type;

    }

}