<template>
  <div class="panel">
    <el-table
      :data="taskData"
      style="width: 100%">
      <el-table-column
        prop="taskId"
        label="编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="userId"
        label="用户编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        label="要求的风格"
        width="100">
        <template slot-scope="scope">
          <span>{{ styleMapper[scope.row.requiredStyleId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="要求的乐器"
        width="100">
        <template slot-scope="scope">
          <span>{{ instrumentMapper[scope.row.requiredInstrumentId]}}</span>
        </template>
      </el-table-column>  
      <el-table-column
        label="要求的场景"
        width="100">
        <template slot-scope="scope">
          <span>{{ sceneMapper[scope.row.requiredSceneId]}}</span>
        </template>
      </el-table-column> 
      <el-table-column
        prop="requiredLength"
        label="要求的长度"
        width="100">
        <template slot-scope="scope">
          <span>{{ Length[scope.row.requiredLength]}}</span>
        </template>
      </el-table-column> 
      <el-table-column
        prop="publishTime"
        label="发布时间"
        width="150">
      </el-table-column> 
      <el-table-column
        label="状态"
        width="80">
        <template slot-scope="scope">
          <span>{{taskStaus[scope.row.status+1]}}</span>
        </template>
      </el-table-column> 
      <el-table-column
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button-group>
            <el-button @click="onDeleteClick(scope.$index)" type="danger" size="small">删除</el-button>
        </el-button-group>
      </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

export default {
  name: 'TaskManagement',
  components:{
  },
  data () {
    return {
      msg: '任务管理',
      taskData:[],
      styles:[],
      instruments:[],
      scenes:[],

      Length:["小于30秒","30秒~1分钟","1分钟~2分钟","2分钟~3分钟","3分钟以上"],

      styleMapper:{},
      instrumentMapper:{},
      sceneMapper:{},

      taskStaus:["未接受","已接受","已完成"],

      getDataStatus:[false,false,false],
    }
  },
  methods:{
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2])
        this.getTaskData();
    },
    onDeleteClick(index){
      this.$confirm('确定删除任务 '+this.taskData[index].taskId+' ?', '删除任务', {
          type: 'warning',
          center: true
        }).then(() => {
          this.deleteConfirm(index);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });

    },
    deleteConfirm(index){
      var form = new FormData();
      form.append("taskId", this.taskData[index].taskId);

      var settings = {
        "url":  this.$backEndUrl+"admin/deleteTask",
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
                this.$message({
                  type: 'success',
                  message: '删除任务成功!'
                });
                this.getTaskData();
            }
            else
            {
                this.$message.error('删除任务失败!');
            } 
      });
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
  },
  created(){
    this.getAllStyle();
    this.getAllInstrument();
    this.getAllScene();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.panel{
  margin:50px 20px;
  min-height: 70vh;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}
</style>
