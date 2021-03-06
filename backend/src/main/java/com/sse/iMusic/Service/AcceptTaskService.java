package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.AcceptTaskMapper;
import com.sse.iMusic.Models.AcceptTask;
import com.sse.iMusic.Mappers.TaskMapper;
import com.sse.iMusic.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class AcceptTaskService {
    @Autowired
    private AcceptTaskMapper acceptTaskMapper;
    @Autowired
    private TaskMapper taskMapper;

    public AcceptTask getAcceptTaskByID(int taskId)
    {
        return acceptTaskMapper.selectByID(taskId);
    }

    public ArrayList<AcceptTask> getAllAcceptTask()
    {
        ArrayList<AcceptTask> tasks=acceptTaskMapper.select();
        for(AcceptTask acceptTask:tasks){
            acceptTask.setTaskData(taskMapper.selectByID(acceptTask.taskId));
        }
        return tasks;
    }

    public int finishTask(Integer taskId,Integer status,Integer musicId)
    {
        return acceptTaskMapper.updateStatusByID(taskId, status,musicId);
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
