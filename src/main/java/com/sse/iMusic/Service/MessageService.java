package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.MessageMapper;
import com.sse.iMusic.Models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public ArrayList<Message> getMessageByID(int senderId,int receiverId)
    {
        return messageMapper.selectByID(senderId,receiverId);
    }

    public ArrayList<Message> getAllMessage()
    {
        return messageMapper.select();
    }

    public ArrayList<Message> getMessageByIDSortByTime(int senderId,int receiverId)
    {
        ArrayList<Message> messages=messageMapper.selectByID(senderId,receiverId);
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                int i = o1.time.compareTo(o2.time);
                if(i>0) return -1;
                else return 1;
            }
        });
        return messages;
    }

    public int addMessage(Integer senderId,Integer receiverId,String content)
    {
        return messageMapper.insert(senderId,receiverId,content);
    }


}
