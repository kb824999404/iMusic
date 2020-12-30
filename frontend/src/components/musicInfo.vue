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
        <div class="Musician">
          <div class="musicianInfo">
            <aplayer theme="#eee" class="musicPlayer"
              :music="{
                title: musicInfo.musicName,
                artist: musicInfo.musicianName,
                src: musicUrl,
                pic: musicImg 
              }"/>
            <div class="musicInfo">
              <div class="names">
                <h2 class="musicName">{{musicInfo.musicName}}</h2>
                <p class="musicianName">{{musicInfo.musicianName}}</p>
              </div>
              <div class="priceAndTime">
                <p class="price">价格：{{musicInfo.price}} 元</p>
                <p class="publishTime">发布时间：{{musicInfo.publishTime}}</p>
              </div>
              <!-- 标签 -->
              <div class="musicianTags">
                <el-tag>{{style.styleName}}</el-tag>
                <el-tag type="success">{{instrument.instrumentName}}</el-tag>
                <el-tag type="warning">{{scene.sceneName}}</el-tag>
              </div>
              <!-- 按钮 -->
              <div class="buttons" v-if="this.$store.state.isLogin">
                <el-button type="primary"
                @click="onButtonClick(1)" v-if="!isStared" round>收藏</el-button>
                <el-button type="info"
                @click="onButtonClick(2)" v-if="isStared" round>取消收藏</el-button>
                <el-button type="primary"
                @click="onButtonClick(3)" v-if="!isPurchased" round>购买</el-button>
                <el-button type="success"
                @click="onButtonClick(4)" v-if="isPurchased" round>下载</el-button>
              </div>
            </div>

          </div>
          <div class="musicDescription">
            <span>{{musicInfo.description}}</span>
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
import Aplayer from 'vue-aplayer'
import musicImg from '../assets/music.png'

export default {
  name: 'MusicInfo',
  components:{
    Navbar,Aplayer
  },
  data () {
    return {
      msg: '音乐信息页',
      musicImg:musicImg,
      musicId:0,
      musicInfo:{
        musicName: "音乐",
        musicianName: "音乐人",
        description: "这是音乐",
        styleId: 1,
        instrumentId: 1,
        sceneId: 1,
        length: 20,
        publishTime: "2020-12-12 19:40:05",
        price: 10,
      },
      musicUrl:"",
      style:{},
      instrument:{},
      scene:{},
      isStared:true,
      isPurchased:true,
    }
  },
  methods:{
    onButtonClick(code){
      switch(code){
        case 1:
          // 收藏
          this.$confirm('确定收藏该音乐吗？', {
              center: true
            }).then(() => {
              this.starMusic();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消操作'
              });          
            });
          break;
        case 2:
        // 取消收藏
        this.$confirm('不再收藏该音乐吗？', {
            center: true
          }).then(() => {
            this.unstarMusic();
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消操作'
            });          
          });
        break;
        case 3:
          // 购买
            this.$confirm('确定购买该音乐吗？', {
              center: true
            }).then(() => {
              this.confirmPurchaseMusic();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消购买'
              });          
            });
          break;
        case 4:
          // 购买
            this.$confirm('要下载该音乐吗？', {
              center: true
            }).then(() => {
              this.downloadMusic();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消操作'
              });          
            });
          break;
          break;
      }
    },
    starMusic(){
      var form = new FormData();
      form.append("musicId", this.musicId);
      form.append("userId",this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"music/starMusic",
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
              this.$message({
                type: 'success',
                message: '收藏音乐成功'
              }); 
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
    confirmPurchaseMusic(){
        this.$confirm('请在新页面完成支付后回到该页面', {
            center: true,
            confirmButtonText: '已完成支付',
            cancelButtonText: '取消支付',
          }).then(() => {
            this.purchaseMusic();
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消支付'
            });          
          });
        window.open("https://www.alipay.com/", '_blank').location;
    },
    purchaseMusic(){
      var form = new FormData();
      form.append("musicId", this.musicId);
      form.append("userId",this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"music/purchaseMusic",
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
              this.$message({
                type: 'success',
                message: '购买音乐成功'
              }); 
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
    unstarMusic(){
      var form = new FormData();
      form.append("musicId", this.musicId);
      form.append("userId",this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"music/unstarMusic",
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
              this.$message({
                type: 'success',
                message: '取消收藏音乐成功'
              }); 
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
    downloadMusic(){
      var a = document.createElement('a');
      a.href =this.musicUrl;
      a.target="blank";
      a.click();
    },
    getMusicInfo(){
      var form = new FormData();
      form.append("musicId", this.musicId);

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
              this.musicInfo=data.musicInfo;
              this.style=data.style;
              this.instrument=data.instrument;
              this.scene=data.scene;
              this.musicUrl=this.$backEndUrl+"/uploadMusic/"+this.musicInfo.fileUrl;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    getStarStatus(){
      var form = new FormData();
      form.append("musicId", this.musicId);
      form.append("userId",this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"music/getStarStatus",
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
              this.isStared=(data.isStared==='true')?true:false;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    },
    getPurchaseStatus(){
      var form = new FormData();
      form.append("musicId",this.musicId);
      form.append("userId",this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"music/getPurchaseStatus",
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
              this.isPurchased=(data.isPurchased==='true')?true:false;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }   
      });
    }
  },
  created(){
    if(this.$route.query.musicId){
      this.musicId=this.$route.query.musicId;
      this.getMusicInfo();
    }
    else
    {
      alert("不能直接访问该页面！");
    }
    if(this.$store.state.isLogin){
      this.getStarStatus();
      this.getPurchaseStatus();
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/musicianInfo.css"></style>
<style scoped>
  .musicPlayer{
    width: 300px;
  }
  .names{
    display: flex;
    flex-direction: row;
    align-items:baseline;

    margin: 5px;
    padding: 5px;
    justify-content: space-around;

    box-shadow: 0 2px 2px 0 rgba(0,0,0,.07), 0 1px 5px 0 rgba(0,0,0,.1);
    background: #fff;
  }
  .musicName{
    font-size: 20px;
    font-weight: 400;
  }
  .musicianName{
    font-size: 16px;
    font-weight: 400;
  }
  .player{
    display: flex;
  }
  .musicianTags {
    margin: 20px 0;
    display: flex;
    justify-content: center;
  }
  .musicianTags .el-tag{
    margin: 0 10px;

  }

  .musicianInfo{
    flex-grow: 0;
    min-width: 250px;
  }
  .musicDescription{
    text-align: left;
    flex-grow: 1;
    box-shadow: 0 2px 5px 0 rgba(0,0,0,.15);
    background: #fff;
    border-radius: 5px;
    height: 300px;
    padding: 10px;
    max-width: 80vh;
  }
  .priceAndTime{
    display: flex;
    padding: 10px;
    box-shadow: 0 2px 2px 0 rgba(0,0,0,.07), 0 1px 5px 0 rgba(0,0,0,.1);
    background: #fff;
    margin: 5px;
  }
  .priceAndTime p{
    font-size: 14px;
    color:#777;
  }
  .priceAndTime .price{
    padding-right: 10px;
  }
  .buttons{
    justify-content: center;
  }
  .buttons .el-button{
    margin: 0 30px;
  }
</style>