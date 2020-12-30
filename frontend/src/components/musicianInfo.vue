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
        <h1 class="pageTitle" v-if="isMe">我的音乐人</h1>
        <div class="Musician">
          <div class="musicianImg">
            <img src="../assets/musician.png"/>
          </div>
          <div class="musicianInfo">
            <h2 class="musicianName">{{musician.musicianName}}</h2>
            <span class="musicianDescription">{{musician.description}}</span>
            <div class="musicianInfoAdd">
                <p class="key">国家</p><p>{{countryMapper[musician.countryId]}}</p>
                <p class="key">喜欢的音乐风格</p><p>{{styleMapper[musician.favMusicStyle]}}</p>
                <p class="key">擅长的乐器</p><p>{{instrumentMapper[musician.favInstrument]}}</p>
            </div>
            <!-- 按钮 -->
            <div class="buttons">
              <el-button type="primary" size="small"
              @click="onButtonClick(0)" v-if="!isMe">私信</el-button>
            </div>
          </div>
        </div>
        <div class="Musics">
          <h2 class="title">音乐人的音乐</h2>
          <el-table
            class="musicTable"
            highlight-current-row
            :data="Music"
            strip>
            <el-table-column
            type="index"
            width="100">
          </el-table-column>
            <el-table-column
              prop="musicName"
              label="名称"
              width="150">
              <template slot-scope="scope">
              <el-button type="text" @click="toMusicInfo(scope.row)">
                {{ scope.row.musicName}}</el-button>
            </template>
            </el-table-column>
            <el-table-column
              label="描述">
              <template slot-scope="scope">
                <span>{{ convertDescription(scope.row.description)}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="风格"
              width="100"
              v-if="getDataStatus[0]">
              <template slot-scope="scope">
                <span>{{ styleMapper[scope.row.styleId]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="乐器"
              width="100"
              v-if="getDataStatus[1]">
              <template slot-scope="scope">
                <span>{{ instrumentMapper[scope.row.instrumentId]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="场景"
              width="100"
              v-if="getDataStatus[2]">
              <template slot-scope="scope">
                <span>{{ sceneMapper[scope.row.sceneId]}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="长度"
              width="150">
              <template slot-scope="scope">
                <span>{{ lengthToStr(scope.row.length)}}</span>
              </template>
            </el-table-column>
          </el-table>
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
import ContentList from '../components/ContentList'
export default {
  name: 'MusicianInfo',
  components:{
    Navbar
  },
  data () {
    return {
      msg: '音乐人信息页',
      musician:{},
      musicianId:0,
      Music:[],
      Style:[],
      Instrument:[],
      Scene:[],
      Country:[],
      isMe:false,

      styleMapper:{},
      instrumentMapper:{},
      sceneMapper:{},
      countryMapper:{},

      getDataStatus:[false,false,false]
    }
  },
  methods:{
    toMusicInfo(item){
      this.$router.push({path:"musicInfo?musicId="+item.musicId});
    },
    onButtonClick(code){
      switch(code){
        case 0:
          this.$router.push({path:"message?receiverId="+this.musician.userId});
          break;
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
    getAllMusic(){
        var form = new FormData();
        form.append("musicianId", this.musicianId);
        var settings = {
          "url": this.$backEndUrl+"musician/getPublishMusic",
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
                    this.Music=data.publishMusic;
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
                this.getDataStatus[2]=true;
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
      form.append("musicianId", this.musicianId);

      var settings = {
        "url": this.$backEndUrl+"musician/getMusicianInfo",
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
              this.musician=data.musicianInfo;
              if(this.$store.state.uid===data.musicianInfo.userId.toString()){
                this.isMe=true;
              }
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }  
      });
 
    }
  },
  created()
  {
    if(this.$route.query.musicianId){
      this.musicianId=this.$route.query.musicianId;
      this.getAllStyle();
      this.getAllInstrument();
      this.getAllScene();
      this.getAllCountry();
      this.getMusicianInfo();
      this.getAllMusic();
    }
    else
    {
      alert("该页面不能直接访问！");
    }


  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/musicianInfo.css"></style>
<style scoped>
 .pageTitle{
    font-size: xx-large;
    font-weight: 400;
    text-align: center;
    color: #409EFF;
    padding: 0;
 }
</style>