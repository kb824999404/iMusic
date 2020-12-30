<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="1"/>
    <!-- 主体 -->
    <el-main>
        
        <!-- 回到顶部按钮 -->
    <div class="block">
      <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item v-for="(item, index) in NewMusic" :key="index">
              <img :src="item.picImg"/>
          </el-carousel-item>
        </el-carousel>
    </div>


    <div class="Page musicPage">
      <h2 class="title">好歌推荐</h2>
      <div style="min-height:50vh;">
          <el-row v-for="row in recommendCard" type="flex" justify="space-around">
              <el-col :span="8" v-for="item in row">
                  <el-row class="musicCard">
                    <el-col :span="6" class="musicImg">
                      <el-image :src="musicImg" fit="cover"/>
                    </el-col>
                    <el-col :span="10" class="musicInfo">
                      <el-button type="text" class="musicName" @click="toMusicInfo(item)">{{item.musicName}}</el-button>
                      <el-button type="text" class="musicianName" @click="toMusicianInfo(item)">{{item.musicianName}}</el-button>
                    </el-col>
                    <el-col :span="6"  class="musicLength"><p>{{lengthToStr(item.length)}}</p></el-col>
                  </el-row>
              </el-col>
          </el-row>
      </div>
    </div>

    <div class="Page musicPage">
      <h2 class="title">新歌首发</h2>
      <div style="min-height:50vh;">
          <el-row v-for="row in newCard" type="flex" justify="space-around">
              <el-col :span="8" v-for="item in row">
                  <el-row class="musicCard">
                    <el-col :span="6" class="musicImg">
                      <el-image :src="musicImg" fit="cover"/>
                    </el-col>
                    <el-col :span="10" class="musicInfo">
                      <el-button type="text" class="musicName" @click="toMusicInfo(item)">{{item.musicName}}</el-button>
                      <el-button type="text" class="musicianName" @click="toMusicianInfo(item)">{{item.musicianName}}</el-button>
                    </el-col>
                    <el-col :span="6"  class="musicLength"><p>{{lengthToStr(item.length)}}</p></el-col>
                  </el-row>
              </el-col>
          </el-row>
      </div>
    </div>

    <div class="Page musicianPage">
        <h2 class="title">音乐人推荐</h2>
        <div class="Musicians" style="min-height:20vh;">
            <div class="musicianCard" v-for="item in recommendMusicians" :key="item.musicianId">
              <el-image :src="musicianImg" fit="cover" class="musicianImg"/>
              <el-button type="text" class="musicianName" 
              @click="toMusicianInfo(item)">{{item.musicianName}}</el-button>
              <p class="musicianCountry">{{countryMapper[item.countryId]}}</p>
            </div>
        </div>
    </div>

        <el-backtop>
        </el-backtop>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar'
 import {NewMusic} from "../assets/data/swiper";
 import musicImg from "../assets/music.png"
 import musicianImg from "../assets/musician.png"
export default {
  name: 'Home',
  components:{
    Navbar,
  
  },
  data () {
    return {
      NewMusic:[],
      recommendMusics:[],
      newMusics:[],
      recommendMusicians:[],

      Country:[],
      countryMapper:{},

      recommendCard:[],
      newCard:[],
      musicImg:musicImg,
      musicianImg:musicianImg

    }
  },
  methods:{
    lengthToStr(len){
      var min=Math.floor(len/60);
      var sec=len%60;
      min=(min>9)?min.toString():("0"+min);
      sec=(sec>9)?sec.toString():("0"+sec);
      var str=min+":"+sec;
      return str;
    },
    toMusicInfo(item){
      this.$router.push({path:"musicInfo?musicId="+item.musicId});
    },
    toMusicianInfo(item){
      this.$router.push({path:"musicianInfo?musicianId="+item.musicianId});
    },
    getRecommendMusic(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"home/getRecommendMusic",
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
                this.recommendMusics=data.recommendMusic;
                this.generateRecommendCard();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }  
      });
    },
    generateRecommendCard(){
        var colNum=3;
        var rowNum = Math.ceil(this.recommendMusics.length / colNum);
        rowNum=(rowNum>3)?3:rowNum;
        var rows = [];
        for (var i = 0; i < rowNum; i++) {
            var row = this.recommendMusics.slice(i * colNum, (i+ 1) * colNum);
            rows.push(row);
        }
        this.recommendCard = rows;
    },
    getNewMusic(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"home/getNewMusic",
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
                this.newMusics=data.newMusic;
                this.generateNewCard();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }  
      });
    },
    generateNewCard(){
        var colNum=3;
        var rowNum = Math.ceil(this.newMusics.length / colNum);
        rowNum=(rowNum>3)?3:rowNum;
        var rows = [];
        for (var i = 0; i < rowNum; i++) {
            var row = this.newMusics.slice(i * colNum, (i+ 1) * colNum);
            rows.push(row);
        }
        this.newCard = rows;
    },
    getRecommendMusician(){
      var form = new FormData();
      form.append("userId", this.$store.state.uid);

      var settings = {
        "url": this.$backEndUrl+"home/getRecommendMusician",
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
                this.recommendMusicians=data.recommendMusician.slice(0,5);
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }  
      });
    },
    getAllCountry(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"musician/getAllCountry",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status == 'true') {
                console.log('success');
                this.Country=data.countrys;
                this.Country.forEach((item)=>{
                  this.countryMapper[item.countryId]=item.countryName;
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
 created()
  {
    this.NewMusic=NewMusic;
    this.getAllCountry();
    this.getRecommendMusic();
    this.getNewMusic();
    this.getRecommendMusician();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/home.css"></style>

<style scoped src="../styles/footer.css"></style>
<style>
  .el-main{
    padding: 0;
  }
   .el-carousel__item h3 {
      color: #475669;
      font-size: 14px;
      opacity: 0.75;
      line-height: 200px;
      margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
      background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
      background-color: #d3dce6;
    }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>