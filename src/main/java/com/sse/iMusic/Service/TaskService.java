package com.sse.iMusic.Service;


import com.sse.iMusic.Mappers.TaskMapper;
import com.sse.iMusic.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    public Task getTaskByID(int taskId)
    {
        return taskMapper.selectByID(taskId);
    }

    public ArrayList<Task> getAllTask()
    {
        return taskMapper.select();
    }

    public ArrayList<Task> getAllTaskSortByTime()
    {
        ArrayList<Task> tasks=taskMapper.select();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                int i = o1.publishTime.compareTo(o2.publishTime);
                if(i>0) return -1;
                else return 1;
            }
        });
        return tasks;
    }

    public int addTask(Integer taskId,Integer userId,
    Integer taskType,String description, Integer styleId,
    Integer instrumentId,Integer sceneId,Integer length)
    {
        return taskMapper.insert(taskId,userId,taskType,description, 
        styleId,instrumentId,sceneId,length);
    }

    public int deleteTask(int taskId)
    {
        return taskMapper.deleteByID(taskId);
    }


}
