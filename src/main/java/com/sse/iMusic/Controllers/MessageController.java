package com.sse.iMusic.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.sse.iMusic.Models.Message;
import com.sse.iMusic.Service.MessageService;
import com.sse.iMusic.Models.User;
import com.sse.iMusic.Service.UserService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/message")
class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @PostMapping("/getMessage")
    @ResponseBody
    public Map<String, Object> getMessage(@RequestParam("senderId") int senderId,
    @RequestParam("receiverId") int receiverId){

        Map<String, Object> result = new HashMap<>();
        User sender=userService.getUserByID(senderId);
        User receiver=userService.getUserByID(receiverId);
        if(sender==null)
        {
            result.put("status","false");
            result.put("message","发送者不存在！");
        }
        else if(receiver==null)
        {
            result.put("status","false");
            result.put("message","接收者不存在！");
        }
        else
        {
            ArrayList<Message> messages=messageService.getMessageByIDSortByTime(senderId,receiverId);
            result.put("status","true");
            result.put("messages",messages);

        }


        return result;
    }


    @PostMapping("/sendMessage")
    @ResponseBody
    public Map<String, Object> sendMessage(@RequestParam("senderId") int senderId,
    @RequestParam("receiverId") int receiverId,
    @RequestParam("content") String content){

        Map<String, Object> result = new HashMap<>();
        User sender=userService.getUserByID(senderId);
        User receiver=userService.getUserByID(receiverId);
        if(sender==null)
        {
            result.put("status","false");
            result.put("message","发送者不存在！");
        }
        else if(receiver==null)
        {
            result.put("status","false");
            result.put("message","接收者不存在！");
        }
        else
        {
            int resultCode=messageService.addMessage(senderId,receiverId,content);
            if(resultCode==1){
                result.put("status","true");
            }
            else{
                result.put("status","false");
                result.put("message","发送信息失败！");
            }
        }


        return result;
    }

}