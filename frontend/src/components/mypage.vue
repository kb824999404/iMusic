<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0"/>
    <!-- 主体 -->
    <el-main>
        <!-- 回到顶部按钮 -->
        <el-backtop>
        </el-backtop>
        <div class="Page">
          <h1 class="title">{{ msg }}</h1>
          <div class="subPage">
            <h2 class="subtitle">个人资料</h2>
            <el-dialog title="修改头像" :visible.sync="dialogVisible" class="avatarDialog" width="300px">
              <div class="dialogContent">
                <el-upload
                  class="avatar-uploader"
                  :action="this.$backEndUrl+'account/uploadAvatar'"
                  :show-file-list="false"
                  :on-success="uploadSuccess"
                  :before-upload="beforeAvatarUpload">
                  <img v-if="tmpImageUrl" :src="tmpImageUrl" class="newavatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <el-button type="primary" @click="modifyAvatar">保存头像</el-button>
              </div>

            </el-dialog>
            <div class="User">
            <div class="avatar">
              <img :src="userAvatar"/>
            </div>
            <div class="userInfo">
              <div class="row userName">
                <p class="key">用户名</p>
                <p>{{userInfo.username}}</p>
              </div>
              <div class="row userName">
                <p class="key">账号</p>
                <p>{{userInfo.userId}}</p>
              </div>
              <div class="row userAvatar">
                <p class="key">头像</p>
                <el-button size="small" type="primary" @click="dialogVisible=true">修改头像</el-button>
              </div>
              <div class="row">
                <p class="key">音乐人</p>
                <el-button type="primary" size="small" v-if="userInfo.userType==1" 
                @click="onMusicianClick(0)">成为音乐人</el-button>
                <el-button-group v-if="userInfo.userType==3">
                  <el-button type="primary" size="small" 
                    @click="onMusicianClick(1)">我的音乐人</el-button>
                    <el-button type="primary" size="small" 
                    @click="onMusicianClick(2)">发布音乐</el-button>
                </el-button-group>
                <p class="userType">{{userTypes[userInfo.userType-1]}}</p>

              </div>
            </div>
        </div>
          </div>
          <div class="subPage">
            <h2 class="subtitle">我的收藏</h2>
            <el-table
              class="musicTable"
              highlight-current-row
              :data="StarMusic">
              <el-table-column
              type="index"
              width="100">
            </el-table-column>
              <el-table-column
                prop="musicName"
                label="名称"
                width="120">
                <template slot-scope="scope">
                  <el-button type="text" @click="toMusicInfo(scope.row)">
                    {{ scope.row.musicName }}</el-button>
                </template>
              </el-table-column>
              <el-table-column
                prop="musicianName"
                label="作者"
                width="120">
                  <template slot-scope="scope">
                  <el-button type="text" @click="toMusicianInfo(scope.row)">
                    {{ scope.row.musicianName }}</el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="风格"
                width="100">
                <template slot-scope="scope">
                  <span>{{ styleMapper[scope.row.styleId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="乐器"
                width="100">
                <template slot-scope="scope">
                  <span>{{ instrumentMapper[scope.row.instrumentId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="场景"
                width="100">
                <template slot-scope="scope">
                  <span>{{ sceneMapper[scope.row.sceneId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="长度"
                width="120">
                <template slot-scope="scope">
                  <span>{{ lengthToStr(scope.row.length)}}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="starTime"
                label="收藏时间">
              </el-table-column>
            </el-table>
          </div>
          <div class="subPage">
            <h2 class="subtitle">我的购买</h2>
            <el-table
              class="musicTable"
              highlight-current-row
              :data="PurchaseMusic"
              strip>
              <el-table-column
              type="index"
              width="100">
            </el-table-column>
              <el-table-column
                prop="musicName"
                label="名称"
                width="120">
                <template slot-scope="scope">
                  <el-button type="text" @click="toMusicInfo(scope.row)">
                    {{ scope.row.musicName }}</el-button>
                </template>
              </el-table-column>
              <el-table-column
                prop="musicianName"
                label="作者"
                width="120">
                <template slot-scope="scope">
                  <el-button type="text" @click="toMusicianInfo(scope.row)">
                    {{ scope.row.musicianName }}</el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="风格"
                width="80">
                <template slot-scope="scope">
                  <span>{{ styleMapper[scope.row.styleId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="乐器"
                width="80">
                <template slot-scope="scope">
                  <span>{{ instrumentMapper[scope.row.instrumentId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="场景"
                width="80">
                <template slot-scope="scope">
                  <span>{{ sceneMapper[scope.row.sceneId]}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="长度"
                width="120">
                <template slot-scope="scope">
                  <span>{{ lengthToStr(scope.row.length)}}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="price"
                label="价格(元)"
                width="100">
              </el-table-column>
              <el-table-column
                prop="purchaseTime"
                label="购买时间">
              </el-table-column>
            </el-table>
          </div>
          <div class="subPage">
            <h2 class="subtitle">我发布的任务</h2>
            <el-table
            :data="taskData"
            @row-dblclick="toTaskInfo"
            highlight-current-row
            style="width: 100%">
            <el-table-column
            type="index"
            width="100">
            </el-table-column>
            <el-table-column
              prop="description"
              label="描述">
              <template slot-scope="scope">
              <el-button type="text" @click="toTaskInfo(scope.row)">{{ scope.row.description}}</el-button>
            </template>
            </el-table-column>
            <el-table-column
              label="要求的风格"
              width="120">
              <template slot-scope="scope">
                <span>{{ styleMapper[scope.row.requiredStyleId]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="要求的乐器"
              width="120">
              <template slot-scope="scope">
                <span>{{ instrumentMapper[scope.row.requiredInstrumentId]}}</span>
              </template>
            </el-table-column>  
            <el-table-column
              label="要求的场景"
              width="120">
              <template slot-scope="scope">
                <span>{{ sceneMapper[scope.row.requiredSceneId]}}</span>
              </template>
            </el-table-column> 
            <el-table-column
              prop="requiredLength"
              label="要求的长度"
              width="200">
              <template slot-scope="scope">
                <span>{{ Length[scope.row.requiredLength]}}</span>
              </template>
            </el-table-column> 
            <el-table-column
              prop="publishTime"
              label="发布时间"
              width="150">
            </el-table-column> 
          </el-table>
          </div>
          <div class="subPage" v-if="this.userInfo.userType==3">
            <h2 class="subtitle">我接受的任务</h2>
            <el-table
            @row-dblclick="toTaskInfo"
            :data="acceptTask"
            highlight-current-row
            style="width: 100%">
            <el-table-column
            type="index"
            width="100">
            </el-table-column>
            <el-table-column
              prop="description"
              label="描述">
              <template slot-scope="scope">
              <el-button type="text" @click="toTaskInfo(scope.row)">{{ scope.row.description}}</el-button>
            </template>
            </el-table-column>
            <el-table-column
              label="要求的风格"
              width="120">
              <template slot-scope="scope">
                <span>{{ styleMapper[scope.row.requiredStyleId]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="要求的乐器"
              width="120">
              <template slot-scope="scope">
                <span>{{ instrumentMapper[scope.row.requiredInstrumentId]}}</span>
              </template>
            </el-table-column>  
            <el-table-column
              label="要求的场景"
              width="120">
              <template slot-scope="scope">
                <span>{{ sceneMapper[scope.row.requiredSceneId]}}</span>
              </template>
            </el-table-column> 
            <el-table-column
              prop="requiredLength"
              label="要求的长度"
              width="200">
              <template slot-scope="scope">
                <span>{{ Length[scope.row.requiredLength]}}</span>
              </template>
            </el-table-column> 
            <el-table-column
              prop="publishTime"
              label="发布时间"
              width="150">
            </el-table-column> 
          </el-table>
          </div>
          <div class="subPage">
            <h2 class="subtitle">最近联系</h2>
            <div class="Friends" style="min-height:20vh;">
            <div class="friendCard" v-for="item in contacts" :key="item.userId">
              <img :src="item.avatar" class="friendAvatar"/>
              <el-button type="text" class="friendName"
              @click="toMessage(item)">{{item.username}}</el-button>
            </div>
          </div>
          </div>
        </div>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar'
import dafaultAvatar from "@/assets/avatar.jpg"

export default {
  name: 'MyPage',
  components:{
    Navbar
  },
  data () {
    return {
      msg: '个人主页',
      StarMusic:[],
      PurchaseMusic:[],
      taskData:[],
      acceptTask:[],
      Style:[],
      Instrument:[],
      Scene:[],
      Country:[],
      tmpImageUrl:"",
      newAvatarUrl:"",
      userAvatar:"",


      dialogVisible:false,

      Length:["小于30秒","30秒~1分钟","1分钟~2分钟","2分钟~3分钟","3分钟以上"],

      styleMapper:{},
      instrumentMapper:{},
      sceneMapper:{},
      countryMapper:{},
      userInfo:{},
      musicianInfo:{},
      userTypes:["您还不是音乐人哦！","您的音乐人认证申请还在审核中哦！","您已经是音乐人了！"],

      getDataStatus:[false,false,false],

      contacts:[],
    }
  },
  methods:{
    toMusicInfo(item){
      this.$router.push({path:"musicInfo?musicId="+item.musicId});
    },
    toMusicianInfo(item){
      this.$router.push({path:"musicianInfo?musicianId="+item.musicianId});
    },
    toTaskInfo(row, column, event){
      this.$router.push({path:"taskInfo?taskId="+row.taskId});
    },
    toMessage(user){
      this.$router.push({path:"message?receiverId="+user.userId});
    },
    onMusicianClick(code){
      switch(code){
        case 0:
          this.$router.push({name:'applyMusician'});
          break;
        case 1:
          this.$router.push({path:'musicianInfo?musicianId='+this.musicianInfo.musicianId});
          break;
        case 2:
          this.$router.push({name:'publishMusic',params:{musicianId:this.musicianInfo.musicianId}});
          break;
      }
    },
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2]){
        this.getStarMusic();
        this.getPurchaseMusic();
        this.getTaskData();
      }
    },
    convertDescription(desc){
      if(desc.length<20){
        return desc;
      }
      else
      {
        return desc.slice(0,20)+"...";
      }
    },
    lengthToStr(len){
      var min=Math.floor(len/60);
      var sec=len%60;
      min=(min>9)?min.toString():("0"+min);
      sec=(sec>9)?sec.toString():("0"+sec);
      var str=min+":"+sec;
      return str;
    },
    uploadSuccess(response, file, fileList){
        var data=response;
        console.log(data);
        if (data.status == 'true') {
            console.log('success');
            this.newAvatarUrl=data.fileUrl;

            this.tmpImageUrl = URL.createObjectURL(file.raw);
        }
        else
        {
            console.log('fail');
            var message = data.message;
            alert(message);
        }
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
    modifyAvatar(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);
      form.append("avatar", this.newAvatarUrl);

      var settings = {
        "url": this.$backEndUrl+"account/modifyAvatar",
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
              this.userInfo=data.userInfo;
              if(data.userInfo.avatar=='Default-Avatar')
                this.userAvatar=dafaultAvatar;
              else
                this.userAvatar=this.$backEndUrl+"avatar/"+this.userInfo.avatar;
              if(this.userInfo.userType==3){
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
              this.getAcceptTask();
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }   
      });
    },
    getStarMusic(){
        var form = new FormData();
        form.append("userId", this.$store.state.uid);
        var settings = {
          "url": this.$backEndUrl+"account/getStarMusic",
          "method": "POST",
          "timeout": 0,
          "processData": false,
          "mimeType": "multipart/form-data",
          "contentType": false,
          "data": form
        };

        this.$jquery.ajax(settings).done((response)=>  {
                var data=JSON.parse(response);
                console.log(data);
                if (data.status === 'true') {
                    console.log('success');
                    this.StarMusic=data.starMusics;
                }
                else
                {
                    console.log('fail');
                    var message = data.message;
                    alert(message);
                } 
        });
    },
    getPurchaseMusic(){
        var form = new FormData();
        form.append("userId", this.$store.state.uid);
        var settings = {
          "url": this.$backEndUrl+"account/getPurchaseMusic",
          "method": "POST",
          "timeout": 0,
          "processData": false,
          "mimeType": "multipart/form-data",
          "contentType": false,
          "data": form
        };

        this.$jquery.ajax(settings).done((response)=>  {
                var data=JSON.parse(response);
                console.log(data);
                if (data.status === 'true') {
                    console.log('success');
                    this.PurchaseMusic=data.purchaseMusics;
                }
                else
                {
                    console.log('fail');
                    var message = data.message;
                    alert(message);
                } 
        });
    },
    getTaskData(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);
      var settings = {
        "url": this.$backEndUrl+"task/getPublishTask",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=>  {
              var data=JSON.parse(response);
              console.log(data);
              if (data.status == 'true') {
                  console.log('success');
                  this.taskData=data.publishTask;
              }
              else
              {
                  console.log('fail');
                  var message = data.message;
                  alert(message);
              } 
      });
    },
    getAcceptTask(){
      var form = new FormData();
      form.append("musicianId", this.musicianInfo.musicianId);
      var settings = {
        "url": this.$backEndUrl+"task/getAcceptTask",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done((response)=>  {
              var data=JSON.parse(response);
              console.log(data);
              if (data.status == 'true') {
                  console.log('success');
                  this.acceptTask=data.acceptTask;
              }
              else
              {
                  console.log('fail');
                  var message = data.message;
                  alert(message);
              } 
      });
    },
    getAllStyle(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"music/getAllStyle",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status === 'true') {
                console.log('success');
                this.Style=data.styles;
                this.Style.forEach((item)=>{
                  this.styleMapper[item.styleId]=item.styleName;
                });
                this.getDataStatus[0]=true;
                this.checkStatus();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
    getAllInstrument(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"music/getAllInstrument",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status === 'true') {
                console.log('success');
                this.Instrument=data.instruments;
                this.Instrument.forEach((item)=>{
                  this.instrumentMapper[item.instrumentId]=item.instrumentName;
                });
                this.getDataStatus[1]=true;
                this.checkStatus();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
    getAllScene(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"music/getAllScene",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status === 'true') {
                console.log('success');
                this.Scene=data.scenes;
                this.Scene.forEach((item)=>{
                  this.sceneMapper[item.sceneId]=item.sceneName;
                });
                this.getDataStatus[2]=true;
                this.checkStatus();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
    getContacts(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"message/getContacts",
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
              this.contacts=data.contacts.slice(0,4);
              this.contacts.forEach((item)=>{
                if(item.avatar=='Default-Avatar')
                  item.avatar=dafaultAvatar;
                else
                  item.avatar=this.$backEndUrl+"avatar/"+item.avatar;
              });
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
    if(this.$store.state.isLogin==="true"){
      this.getUserInfo();
      this.getAllStyle();
      this.getAllInstrument();
      this.getAllScene();
      this.getContacts();
    }
    else
    {
      alert("请先登录！");
    }



  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped>
  .el-main{
    padding: 0;
  }
  .Page{
    background: linear-gradient(#f3f3f3,#fff);
    padding: 0 5%;
    margin: 0 2%;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,.15);
    border-radius: 10px;
    overflow: hidden;

  }  
  .title{
    padding: 3% 0;
    font-size: 32px;
    font-weight: 400;
    margin: 0;
    color: #409EFF;
  }
  .subPage{
    text-align: left;
    padding:2% 5%;
    border-top: 2px dashed #aaa;
  }
  .subtitle{
    font-size: 24px;
    font-weight: 400;
    color: #409EFF;
  }
    /* 用户信息 */
  .User{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
  }
  .avatar{
    text-align: left;
    flex-grow: 0;
  }
  .avatar img{
    width: 250px;
    height: 250px;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,.15);
    border-radius: 50%;
    object-fit:  cover;

  }
  .userInfo{
    text-align: left;
    flex-grow: 1;
    padding:0 5%;
  }

  .userInfo .row{
    display: flex;
    align-items: center;
  }
  .userInfo .row .el-button{
    padding: 10px;
    height: 40px;
  }
  .userInfo .row .key{
    width: 100px;
    font-size: 16px;
    font-weight: bold;
  }

  .userType{
    padding-left: 10px;
    color: #aaa;
  }

  /* 修改头像 */
  .dialogContent{
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
  }
  .dialogContent .el-button{
    margin-top: 20px;
  }
  .avatar-uploader{
    border:2px dashed #d9d9d9;
    border-radius: 6px;
    height: 178px;
    overflow: hidden;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    cursor: pointer;
    margin: auto;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .newavatar {
    width: 178px;
    height: 178px;
    display: block;
    object-fit:  cover;

  }

  /* 联系人 */
  .Friends{
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-wrap: wrap;
  }
  .friendCard{
      padding: 0;
      width: 100px;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin: 10px 50px;
  }
  .friendAvatar{
    width:100px;
    height:100px;
    object-fit: cover;
    border-radius: 50%;
    box-shadow: 0 2px 10px 0 rgba(0,0,0,.15);
    margin: auto;

  }
  .friendName{
    text-align: center;
  }
</style>
