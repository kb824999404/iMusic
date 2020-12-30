<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="4"/>
    <!-- 主体 -->
    <el-main>
        <!-- 回到顶部按钮 -->
        <el-backtop>
        </el-backtop>
        <div class="Page">
        <div class="title">
          <h1>任务广场</h1>
        </div>
      <p class="searchResult">找到 {{taskData.length}} 个任务</p>
      <el-table
        :data="taskData"
        highlight-current-row
        @row-dblclick="toTaskInfo"
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
          width="200">
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
import Navbar from '@/components/navbar'

export default {
  name: 'Task',
  components:{
    Navbar
  },
  data () {
    return {
      taskData:[],
      styles:[],
      instruments:[],
      scenes:[],

      Length:["小于30秒","30秒~1分钟","1分钟~2分钟","2分钟~3分钟","3分钟以上"],

      styleMapper:{},
      instrumentMapper:{},
      sceneMapper:{},

      getDataStatus:[false,false,false],

    }
  },
  created()
  {
    this.getAllStyle();
    this.getAllInstrument();
    this.getAllScene();

  },
  methods:
  {
    toTaskInfo(row, column, event){
      this.$router.push({path:"taskInfo?taskId="+row.taskId});
    },
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2])
        this.getTaskData();
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
    getTaskData(){
      var form = new FormData();
          var settings = {
            "url": this.$backEndUrl+"admin/getAllTask",
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
                      this.taskData=data.tasks;
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
<style scoped>
  .title{
    padding: 1% 2%;
    border-bottom: 2px dashed #aaa;
  }
  .title h1{
    font-size: xx-large;
    font-weight: 400;
    color: #409EFF;
    text-align: left;
  }
</style>

