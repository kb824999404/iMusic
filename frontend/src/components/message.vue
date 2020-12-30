<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0" />
    <!-- 主体 -->
    <el-main>
      <el-container class="chatBox">
        <el-header class="chatBox-head">
          <div class="ChatInfoName">{{receiverInfo.username}}</div>
          <div class="chat-close" @click="onCloseClick">关闭</div>
        </el-header>

        <el-main class="chat-main">


          <div class="clearfloat" v-for="mess in message">
            <div class="chat-time">{{mess.time}}</div>
            <div class="left" v-if="mess.sender===1">
              <img :src="receiverAvatar" class="chat-avatars">
              <div class="chat-message">{{mess.content}}</div>
            </div>
            <div class="right" v-if="mess.sender===0">
              <div class="chat-message">{{mess.content}}</div>
              <img :src="senderAvatar" class="chat-avatars">
            </div>
          </div>

        </el-main>

        <el-footer class="chatBox-send">
          <!-- <div class="div-textarea" contenteditable="true"></div> -->
          <el-input v-model="sendMessage"></el-input>
          <el-button type="primary" size="small" class="chatBox-send" @click="onSendClick">发送</el-button>
        </el-footer>
      </el-container>

      <el-backtop> </el-backtop>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from "@/components/navbar";
import dafaultAvatar from "@/assets/avatar.jpg"

export default {
  name: "Message",
  components: {
    Navbar,
  },
  data() {
    return {
      msg: "信息交流页",
      senderId:-1,
      receiverId:-1,
      senderAvatar:"",
      receiverAvatar:"",
      senderInfo:{},
      receiverInfo:{},


      message:[],

      myHead:"",
      yourHead:"",

      sendMessage:""
    };
  },
  methods:{
    getMessages(){
        var form = new FormData();
        form.append("senderId", this.senderId);
        form.append("receiverId", this.receiverId);

        var settings = {
          "url": this.$backEndUrl+"message/getMessage",
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
                this.message = data.messages;
                console.log(this.message);
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }  
        });
    },
    onCloseClick(){
      this.$router.go(-1);
    },
    onSendClick(){
        var form = new FormData();
        form.append("senderId", this.senderId);
        form.append("receiverId", this.receiverId);
        form.append("content", this.sendMessage);

        var settings = {
          "url":  this.$backEndUrl+"message/sendMessage",
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
                this.sendMessage="";
                this.getMessages();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }  
      });
    },
    getSenderInfo(){
      var form = new FormData();
      form.append("userId", this.senderId);

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
              this.senderInfo=data.userInfo;
              if(data.userInfo.avatar=='Default-Avatar')
                this.senderAvatar=dafaultAvatar;
              else
                this.senderAvatar=this.$backEndUrl+"avatar/"+this.senderInfo.avatar;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }   
      });
    },
    getReceiverInfo(){
      var form = new FormData();
      form.append("userId", this.receiverId);

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
              this.receiverInfo=data.userInfo;
              if(data.userInfo.avatar=='Default-Avatar')
                this.receiverAvatar=dafaultAvatar;
              else
                this.receiverAvatar=this.$backEndUrl+"avatar/"+this.receiverInfo.avatar;
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
    if(this.$store.state.isLogin){
      this.senderId=this.$store.state.uid;
      if(this.$route.query.receiverId){
        this.receiverId=this.$route.query.receiverId;
        this.getMessages();
        this.getSenderInfo();
        this.getReceiverInfo();
      }
      else
      {
        alert("不能直接访问该页面！");
      }
    }
    else
    {
        alert("请先登录！");
    }

  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/message.css"></style>