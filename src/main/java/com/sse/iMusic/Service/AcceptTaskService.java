package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.AcceptTaskMapper;
import com.sse.iMusic.Models.AcceptTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class AcceptTaskService {
    @Autowired
    private AcceptTaskMapper acceptTaskMapper;

    public AcceptTask getAcceptTaskByID(int taskId)
    {
        return acceptTaskMapper.selectByID(taskId);
    }

    public ArrayList<AcceptTask> getAllAcceptTask()
    {
        return acceptTaskMapper.select();
    }


    public int addTask(Integer taskId,Integer musicianId,Integer status)
    {
        return acceptTaskMapper.insert(taskId,musicianId,status);
    }

    public int deleteTask(int taskId)
    {
        return acceptTaskMapper.deleteByID(taskId);
    }


}
