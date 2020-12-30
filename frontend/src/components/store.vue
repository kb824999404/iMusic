<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="2"/>
    <!-- 主体 -->
    <el-main>
        <!-- 回到顶部按钮 -->
        <el-backtop>
        </el-backtop>
        <div class="Page">
          <!-- 选择栏 -->
          <div class="selectBar">
            <h1>筛选你的音乐</h1>
            <!-- 选择器 -->
            <div class="selecters">
                <el-select v-model="selectStyle" placeholder="风格">
                  <el-option
                    v-for="item in Style"
                    :key="item.styleId"
                    :label="item.styleName"
                    :value="item.styleId">
                  </el-option>
                </el-select>
                <el-select v-model="selectInstrument" placeholder="乐器">
                  <el-option
                    v-for="item in Instrument"
                    :key="item.instrumentId"
                    :label="item.instrumentName"
                    :value="item.instrumentId">
                  </el-option>
                </el-select>
                <el-select v-model="selectScene" placeholder="场景">
                  <el-option
                    v-for="item in Scene"
                    :key="item.sceneId"
                    :label="item.sceneName"
                    :value="item.sceneId">
                  </el-option>
                </el-select>
                <el-select v-model="selectLength" placeholder="时长">
                  <el-option
                    v-for="item in Length"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id">
                  </el-option>
                </el-select>
                <el-button type="primary" @click="refreshMusicData">筛选</el-button>
            </div>
            <!-- 标签 -->
            <div class="tags">
              <el-tag v-if="hasSearchKey" type="info">
                  {{searchMusicName}}
              </el-tag>
              <el-tag v-if="selectStyle!=''">
                  {{styleMapper[selectStyle]}}
              </el-tag>
              <el-tag v-if="selectInstrument!=''" type="success">
                  {{instrumentMapper[selectInstrument]}}
              </el-tag>
              <el-tag v-if="selectScene!=''" type="warning">
                  {{sceneMapper[selectScene]}}
              </el-tag>
            </div>
          </div>
      <p class="searchResult">找到 {{Music.length}} 首单曲</p>
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
        width="120">
        <template slot-scope="scope">
          <el-button type="text" @click="toMusicInfo(scope.row)">
            {{ scope.row.musicName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="musicianName"
        label="作者"
        width="120">
          <template slot-scope="scope">
          <el-button type="text" @click="toMusicianInfo(scope.row)">
            {{ scope.row.musicianName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="描述">
        <template slot-scope="scope">
          <span>{{ convertDescription(scope.row.description)}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="publishTime"
        label="发布时间"
        width="200">
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
        width="150">
        <template slot-scope="scope">
          <span>{{ lengthToStr(scope.row.length)}}</span>
        </template>
      </el-table-column>
    </el-table>
        </div>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar';


export default {
  name: 'Store',
  components:{
    Navbar
  },
  data () {
    return {
    Music:[],
    Style:[],
    Instrument:[],
    Scene:[],
    Length:[
      {
        id:0,
        text:"小于30秒"
      },
      {
        id:1,
        text:"30秒~1分钟"
      },
      {
        id:2,
        text:"1分钟~2分钟"
      },
      {
        id:3,
        text:"2分钟~3分钟"
      },
      {
        id:4,
        text:"3分钟以上"
      },
    ],
    selectStyle:"",
    selectInstrument:"",
    selectScene:"",
    selectLength:"",
    searchMusicName:"",
    hasSearchKey:false,

    styleMapper:{},
    instrumentMapper:{},
    sceneMapper:{},

    getDataStatus:[false,false,false]

    }
  },
  created()
  {
    if(this.$route.params.musicName){
      this.searchMusicName=this.$route.params.musicName;
      this.hasSearchKey=true;
    }
    this.getAllStyle();
    this.getAllInstrument();
    this.getAllScene();

  },
  methods:
  {
    toMusicInfo(item){
      this.$router.push({path:"musicInfo?musicId="+item.musicId});
    },
    toMusicianInfo(item){
      this.$router.push({path:"musicianInfo?musicianId="+item.musicianId});
    },
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2]){
        this.getAllMusic();
      }
    },
    convertDescription(desc){
      if(desc.length<15){
        return desc;
      }
      else
      {
        return desc.slice(0,15)+"...";
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
    refreshMusicData(){
      this.Music=[];
      this.getAllMusic();
    },
    getAllMusic(){
        var form = new FormData();
        var styleId=(this.selectStyle==="")?-1:this.selectStyle;
        var instrumentId=(this.selectInstrument==="")?-1:this.selectInstrument;
        var sceneId=(this.selectScene==="")?-1:this.selectScene;
        var length=(this.selectLength==="")?-1:this.selectLength;
        if(this.hasSearchKey){
          var searchKey=this.searchMusicName.split(" ");
        }
        else{
          var searchKey=[];
        }
        form.append("styleId", styleId);
        form.append("instrumentId", instrumentId);
        form.append("sceneId", sceneId);
        form.append("length", length);
        form.append("searchKey", searchKey);
        var settings = {
          "url": this.$backEndUrl+"music/getAllMusic",
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
                    this.Music=data.musics;
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
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/store.css"></style>
