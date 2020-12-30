<template>
    <el-header class="header" height="100px">
        <el-menu id="navbar" mode="horizontal" :default-active="activeIndex" class="navbar">
            <div class="navbar-item-blank-left"></div>
            <!-- <img class="logo"/> -->
            <el-menu-item class="navbar-item" index="1"><a :href="href[0]">首页</a></el-menu-item>
            <el-menu-item class="navbar-item" index="2"><a :href="href[1]">音乐商店</a></el-menu-item>
            <el-menu-item class="navbar-item" index="3"><a :href="href[2]">音乐人</a></el-menu-item>
            <el-menu-item class="navbar-item" index="4"><a :href="href[3]">任务广场</a></el-menu-item>
            <div class="navbar-item-blank-center">
                <el-input class="form-input" v-model="search" placeholder="请输入音乐名或描述">
                    <el-button slot="suffix" class="fa fa-search search-button" type="text"
                    @click="onSearchClick">
                        <font-awesome-icon icon="search" />
                    </el-button>
                </el-input>
            </div>
            <el-submenu class="navbar-menu" index="5" v-if="isLogin">
                <template slot="title">
                    <img :src="userAvatar" class="avatar"/>
                </template>
                <el-menu-item class="navbar-item" index="5-1">
                    <a :href="subhref[0]">个人主页</a>
                </el-menu-item>
                <el-menu-item class="navbar-item" index="5-2">
                    <a :href="subhref[1]">发布任务</a>
                </el-menu-item>
                <el-menu-item class="navbar-item" index="5-4" @click="logout">
                    <a >登出</a>
                </el-menu-item>
            </el-submenu>
            <el-menu-item class="navbar-item" index="6" v-if="!isLogin"><a :href="href[4]">登录</a></el-menu-item>
            <el-menu-item class="navbar-item" index="7" v-if="!isLogin"><a :href="href[5]">注册</a></el-menu-item>
            <div class="navbar-item-blank-right"></div>
        </el-menu>
    </el-header>
</template>

<script>
import dafaultAvatar from "@/assets/avatar.jpg"

export default {
  name: 'Navbar',
  props: {
    activeIndex: {
      type: String,
      default: '1'
    }
  },
  computed:{
      isLogin(){
          if(this.$store.state.isLogin==='true'){
              return true;
          }
          else
            return false;
      }
  },
  data () {
    return {
        search:"",
        href:[
            location.origin,
            location.origin+'/store',
            location.origin+'/musician',
            location.origin+'/task',
            location.origin+'/login',
            location.origin+'/register'
        ],
        subhref:[
            location.origin+'/mypage',
            location.origin+'/publishtask',
        ],
        userAvatar:"",
    }
  },
  methods:{
    logout: function () {       //退出登录
        console.log('logout');
        localStorage.setItem('isLogin',false);
        localStorage.setItem('uid',-1);
        this.$store.state.isLogin=false;
        this.$store.state.uid=-1;
        this.$router.push({name:"home"});

    },
    onSearchClick(){
        if(this.$route.name!='store'){
            this.$router.push({ name: 'store', params: { musicName:this.search }});
        }
        else
        {
            this.$router.replace({ name: 'blank', params: { musicName:this.search }});
        }
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
            if(data.userInfo.avatar=='Default-Avatar')
                this.userAvatar=dafaultAvatar;
            else
                this.userAvatar=this.$backEndUrl+"avatar/"+data.userInfo.avatar;
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
      if(this.isLogin){
        this.getUserInfo();
      }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/navbar.css"></style>