<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0"/>
    <!-- 主体 -->
    <el-main>
        <!-- 回到顶部按钮 -->
        <el-backtop>
        </el-backtop>
        <div class="infoPage" v-loading="!getDataStatus">
          <h1 class="title" v-if="myUserId==userInfo.userId">我的任务</h1>
          <h1 class="title" v-else>任务信息</h1>

          <el-form
            label-width="80px"
            class="infoForm"
          >
            <el-form-item label="发布人">
              <el-button type="text" class="value" @click="toMessage">{{userInfo.username}}</el-button>
            </el-form-item>
            <el-form-item label="任务描述">
              <span class="value">{{task.description}}</span>
            </el-form-item>
            <el-form-item label="要求风格">
              <el-tag class="value">{{style.styleName}}</el-tag>
            </el-form-item>
            <el-form-item label="要求乐器">
              <el-tag type="success" class="value">{{instrument.instrumentName}}</el-tag>
            </el-form-item>
            <el-form-item label="要求场景">
              <el-tag type="warning" class="value">{{scene.sceneName}}</el-tag>
            </el-form-item>
            <el-form-item label="发布时间">
              <span class="value">{{Length[task.requiredLength]}}</span>
            </el-form-item>
            <el-form-item label="发布时间">
              <span class="value">{{task.publishTime}}</span>
            </el-form-item>
            <el-form-item label="任务状态">
              <span class="value">{{taskStatus}}</span>
            </el-form-item>
              <el-button type="primary" class="acceptButton" round
              v-if="acceptButtonDisplay" @click="acceptTask">接受任务</el-button>
              <el-button type="warning" class="acceptButton" round
              v-if="cancelButtonDisplay" @click="unacceptTask">取消任务</el-button>
              <el-button type="danger" class="acceptButton" round
              v-if="finishButtonDisplay" @click="dialogVisible=true">完成任务</el-button>
            <el-form-item label="音乐" v-if="musicDisplay">
              <el-button type="text" class="value" v-if="taskMusicInfo.checked==1"
              @click="toMusic">{{taskMusicInfo.musicName}}</el-button>
              <span class="value" v-if="taskMusicInfo.checked==0">审核中</span>
            </el-form-item>
          </el-form>
        </div>
    <!-- 完成任务对话框 -->
    <el-dialog title="完成任务" :visible.sync="dialogVisible" width="40%">
      <el-row>
        <el-col :span="6"><p class="infoKey">歌名：</p></el-col>
        <el-col :span="18"><el-input v-model="musicInfo.name"></el-input></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">歌曲描述：</p></el-col>
        <el-col :span="18"><el-input type="textarea" v-model="musicInfo.description"></el-input></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">价格：</p></el-col>
        <el-col :span="18">
          <el-input-number v-model="musicInfo.price" :min="0" label="" :step="1"></el-input-number>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">风格：</p></el-col>
        <el-col :span="18"><p>{{style.styleName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">乐器：</p></el-col>
        <el-col :span="18"><p>{{instrument.instrumentName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">场景：</p></el-col>
        <el-col :span="18"><p>{{scene.sceneName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">长度：</p></el-col>
        <el-col :span="18">
          <el-input-number v-model="musicInfo.musicLength" :min="0" label="" :step="1"></el-input-number>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">音频：</p></el-col>
        <el-col :span="18">
        <el-upload
          class="upload-demo"
          :action="this.$backEndUrl+'music/uploadMusic'"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          :on-success="uploadSuccess"
          :on-remove="handleRemove">
          <el-button size="small" type="primary">点击上传音频</el-button>
        </el-upload>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6"><p class="infoKey">附件：</p></el-col>
        <el-col :span="18">
          <el-upload
            class="upload-demo"
            multiple
            :limit="1"
            :action="this.$backEndUrl+'music/uploadAppendix'"
            :on-exceed="handleAppendixExceed"
            :on-success="uploadAppendixSuccess"
            :on-remove="handleAppendixRemove"
          >
            <el-button size="small" type="primary" :disabled="this.musicianId==0">点击上传文件</el-button>
          </el-upload>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" round>取 消</el-button>
        <el-button type="primary" @click="finishTask" round>确 定</el-button>
      </div>
    </el-dialog>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar'

export default {
  name: 'TaskInfo',
  components:{
    Navbar
  },
  computed:{
    acceptButtonDisplay(){
      if(this.$store.state.isLogin&&this.myUserInfo.userType==3)
      {
        if(this.isAccept) return false;
        else return true;
      }
      else
        return false;
    },
    cancelButtonDisplay(){
      if(this.$store.state.isLogin&&this.myUserInfo.userType==3&&this.isAccept
      &&this.acceptInfo.musicianId==this.musicianInfo.musicianId&&this.acceptInfo.status==0)
        return true;
      else
        return false;
    },
    finishButtonDisplay(){
      if(this.$store.state.isLogin&&this.myUserInfo.userType==3&&this.isAccept
      &&this.acceptInfo.musicianId==this.musicianInfo.musicianId&&this.acceptInfo.status==0)
        return true;
      else
        return false;
    },
    musicDisplay(){
      if(this.$store.state.isLogin&&this.myUserInfo.userType==3&&this.isAccept
      &&this.acceptInfo.musicianId==this.musicianInfo.musicianId&&this.acceptInfo.status==1)
        return true;
      else
        return false;
    },
    taskStatus(){
      if(!this.isAccept){
        return "未接受";
      }
      else
      {
        if(this.acceptInfo.status==0)
          return "已接受";
        else
          return "已完成";
      }
    }
  },
  data () {
    return {
      taskId:1,
      myUserId:-1,
      task:{},
      Length:["小于30秒","30秒~1分钟","1分钟~2分钟","2分钟~3分钟","3分钟以上"],
      style:{},
      instrument:{},
      scene:{},
      userInfo:{},
      getDataStatus:false,
      myUserInfo:{},
      musicianInfo:{},
      isAccept:true,
      acceptInfo:{},

      taskMusicInfo:{},

      musicUrl:"",
      uploadStatus:false,
      appendixUrl:"",
      uploadAppendixStatus:false,

      musicInfo:{
        name:"",
        description:"",
        price:0,
        musicLength:0,
      },

      dialogVisible:false
    }
  },
  methods:{
    toMessage(){
      if(this.myUserId!=-1&&this.myUserId!=this.userInfo.userId){
        this.$router.push({path:"message?receiverId="+this.userInfo.userId});
      }
    },
    toMusic(){
      this.$router.push({path:"musicInfo?musicId="+this.taskMusicInfo.musicId});
    },
    handleExceed() {
      this.$message.warning(
        `一次只能上传一个音频！`
      );
    },
    handleRemove(){
      this.uploadStatus=false;
    },
    uploadSuccess(response, file, fileList){
        var data=response;
        console.log(data);
        if (data.status == 'true') {
            console.log('success');
            this.musicUrl=data.fileUrl;
            this.uploadStatus=true;
        }
        else
        {
            console.log('fail');
            var message = data.message;
            alert(message);
        }
    },
    handleAppendixExceed() {
      this.$message.warning(
        `一次只能上传一个附件！`
      );
    },
    handleAppendixRemove(){
      this.uploadAppendixStatus=false;
    },
    uploadAppendixSuccess(response, file, fileList){
        var data=response;
        console.log(data);
        if (data.status == 'true') {
            console.log('success');
            this.appendixUrl=data.fileUrl;
            this.uploadAppendixStatus=true;
        }
        else
        {
            console.log('fail');
            var message = data.message;
            alert(message);
        }
    },
    postForm(){
      var form = new FormData();
      form.append("taskId", this.taskId);
      form.append("musicianId", this.musicianInfo.musicianId);
      form.append("musicName", this.musicInfo.name);
      form.append("description", this.musicInfo.description);
      form.append("styleId",this.style.styleId);
      form.append("instrumentId", this.instrument.instrumentId);
      form.append("sceneId", this.scene.sceneId);
      form.append("length", this.musicInfo.musicLength);
      form.append("price", this.musicInfo.price);
      form.append("fileUrl", this.musicUrl);
      form.append("appendixUrl", this.appendixUrl);

      var settings = {
        "url": this.$backEndUrl+"task/finishTask",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done(function (response) {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }
      });
    },
    finishTask() {
      if (
        this.musicInfo.name == "" ||
        this.musicInfo.description == "" ||
        !this.uploadStatus ||
        !this.uploadAppendixStatus
      ) {
        this.$alert("请完善表单信息！", "无法提交", {
          confirmButtonText: "确定",
          callback: (action) => {
            this.$message({
              type: "alert",
              message: "请完善表单信息！",
            });
          },
        });
      } else {
        this.postForm();
        this.$alert("您的音乐信息以提交成功，请等待审核！", "提交成功", {
          confirmButtonText: "确定",
          callback: (action) => {
            this.$message({
              type: "success",
              message: "申请提交成功!",
            });
          },
        });
      }
    },
    getTaskInfo(){
      var form = new FormData();
      form.append("taskId", this.taskId);

      var settings = {
        "url": this.$backEndUrl+"task/getTaskInfo",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              this.task=data.taskInfo;
              this.style=data.style;
              this.instrument=data.instrument;
              this.scene=data.scene;
              this.userInfo=data.user;
              this.getDataStatus=true;

              this.getAcceptStatus();
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    getAcceptStatus(){
      var form = new FormData();
      form.append("taskId", this.taskId);

      var settings = {
        "url": this.$backEndUrl+"task/getAcceptStatus",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              if(data.isAccept=='false')
                this.isAccept=false;
              else
              {
                this.isAccept=true;
                this.acceptInfo=data.acceptTask;
                if(this.acceptInfo.status==1){
                  this.getTaskMusicInfo();
                }
              }
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    acceptTask(){
      var form = new FormData();
      form.append("taskId", this.taskId);
      form.append("musicianId", this.musicianInfo.musicianId);

      var settings = {
        "url": this.$backEndUrl+"task/acceptTask",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              alert("接受任务成功！");
              this.$router.go(0);
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    unacceptTask(){
      var form = new FormData();
      form.append("taskId", this.taskId);

      var settings = {
        "url": this.$backEndUrl+"task/unacceptTask",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              alert("取消任务成功！");
              this.$router.go(0);
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    getUserInfo(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"account/getUserInfo",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              this.myUserInfo=data.userInfo;
              if(this.myUserInfo.userType==3){
                this.getMusicianInfo();
              }
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }   
      });
    },
    getMusicianInfo(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"account/getMyMusicianInfo",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              this.musicianInfo=data.musicianInfo;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }   
      });
    },
    getTaskMusicInfo(){
      var form = new FormData();
      form.append("musicId", this.acceptInfo.musicId);

      var settings = {
        "url": this.$backEndUrl+"music/getMusicInfo",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              this.taskMusicInfo=data.musicInfo;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }   
      });
    },
  },
  created(){
    if(this.$route.query.taskId){
      this.taskId=this.$route.query.taskId;
      if(this.$store.state.isLogin){
        this.myUserId=this.$store.state.uid;
        this.getUserInfo();
      }
      this.getTaskInfo();
    }
    else
    {
      alert("该页面不能直接访问！");
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped>
  .infoPage{
    width: 80vh;
    margin: auto;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    padding: 1vh 5vh;
  }
  .el-form{
    width: 50%;
    margin: auto;
  }
  .el-form-item{
    text-align: left;
    margin: 10px 0;

  }
  .value{
    margin-left: 20px;
  }
  .acceptButton{
    margin: 20px 0;
  }

  .panel{
    margin:50px 20px;
    min-height: 70vh;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  }
  .infoKey{
      color: #1E90FF;
      text-align: right;
  }
  .checkSwitch{
    padding: 15px;
  }

  .dialog-footer{
    text-align: center !important;
  }
</style>