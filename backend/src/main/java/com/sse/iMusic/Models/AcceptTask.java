package com.sse.iMusic.Models;
import com.sse.iMusic.Models.Task;

public class AcceptTask {
    public Integer taskId;
    public Integer musicianId;
    public Integer status;
    public String acceptTime;
    public String finishTime;
    public Integer musicId;

    public Integer userId;
    public String description;
    public Integer requiredStyleId;
    public Integer requiredInstrumentId;
    public Integer requiredSceneId;
    public Integer requiredLength;
    public String publishTime;

    public void setTaskData(Task task){
        if(task==null)  return;
        this.userId=task.userId;
        this.description=task.description;
        this.requiredStyleId=task.requiredStyleId;
        this.requiredInstrumentId=task.requiredInstrumentId;
        this.requiredSceneId=task.requiredSceneId;
        this.requiredLength=task.requiredLength;
        this.publishTime=task.publishTime;   
    }

}