<template>
  <div class="panel">
    <el-table
      :data="musicData"
      style="width: 100%">
      <el-table-column
        prop="musicId"
        label="编号"
        width="50">
      </el-table-column>
      <el-table-column
        prop="musicName"
        label="名称"
        width="80">
      </el-table-column>
      <el-table-column
        prop="musicianId"
        label="音乐人编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        label="风格"
        width="50">
        <template slot-scope="scope">
          <span>{{ styleMapper[scope.row.styleId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="乐器"
        width="50">
        <template slot-scope="scope">
          <span>{{ instrumentMapper[scope.row.instrumentId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="场景"
        width="50">
        <template slot-scope="scope">
          <span>{{ sceneMapper[scope.row.sceneId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="长度"
        width="80">
        <template slot-scope="scope">
          <span>{{ lengthToStr(scope.row.length)}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="publishTime"
        label="发布时间"
        width="150">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格(元)"
        width="80">
      </el-table-column>
      <el-table-column
        label="审核状态"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.checked==1?"已审核":"未审核" }}</span>
        </template>
      </el-table-column>
      <el-table-column
      label="操作"
      width="150">
      <template slot-scope="scope">
        <el-button-group>
            <el-button @click="onModifyClick(scope.$index)" type="primary" size="small">审核</el-button>
            <el-button @click="onDeleteClick(scope.$index)" type="danger" size="small">删除</el-button>
        </el-button-group>
      </template>
      </el-table-column>
    </el-table>

    <!-- 审核音乐对话框 -->
    <el-dialog title="审核音乐" :visible.sync="dialogVisible" width="30%">
      <el-row>
        <el-col :span="8"><p class="infoKey">编号：</p></el-col>
        <el-col :span="16"><p>{{form.musicId}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">名称：</p></el-col>
        <el-col :span="16"><p>{{form.musicName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">作者：</p></el-col>
        <el-col :span="16"><p>{{form.musicianName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">描述：</p></el-col>
        <el-col :span="16"><p>{{form.description}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">风格：</p></el-col>
        <el-col :span="16"><p>{{styleMapper[form.styleId]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">乐器：</p></el-col>
        <el-col :span="16"><p>{{instrumentMapper[form.instrumentId]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">场景：</p></el-col>
        <el-col :span="16"><p>{{sceneMapper[form.sceneId]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">长度：</p></el-col>
        <el-col :span="16"><p>{{lengthToStr(form.length)}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">发布时间：</p></el-col>
        <el-col :span="16"><p>{{form.publishTime}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">价格：</p></el-col>
        <el-col :span="16"><p>{{form.price}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">附件：</p></el-col>
        <el-col :span="16"><el-link 
        :href="this.$backEndUrl+'musicAppendix/'+form.appendix"><p>查看附件</p></el-link></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">审核状态：</p></el-col>
        <el-col :span="16">
          <el-switch class="checkSwitch"
            v-model="form.checked"
            active-text="已审核"
            inactive-text="未审核">
          </el-switch>
        </el-col>
      </el-row>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: 'MusicManagement',
  components:{
  },
  data () {
    return {
      msg: '音乐管理',
      musicData: [],
      styles:[],
      instruments:[],
      scenes:[],

      styleMapper:{},
      instrumentMapper:{},
      sceneMapper:{},

      getDataStatus:[false,false,false],


      dialogVisible:false,
      form: {
          musicId: 0,
          musicianId: 0,
          musicName: "Music",
          description: "Description",
          styleId: 0,
          instrumentId: 0,
          sceneId: 0,
          length: 0,
          publishTime: "xxxx-xx-xx xx:xx:xx",
          price: 0,
          checked: false
      },
    }
  },
  methods:{
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2])
        this.getMusicData();
    },
    lengthToStr(len){
      var min=Math.floor(len/60);
      var sec=len%60;
      min=(min>9)?min.toString():("0"+min);
      sec=(sec>9)?sec.toString():("0"+sec);
      var str=min+":"+sec;
      return str;
    },
    getMusicData(){
        var form = new FormData();
        var settings = {
          "url": this.$backEndUrl+"admin/getAllMusic",
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
                    this.musicData=data.musics;
                }
                else
                {
                    console.log('fail');
                    var message = data.message;
                    alert(message);
                } 
        });
    },
    onModifyClick(index){
      console.log(index);
      this.showModifyDialog(index);
    },
    onDeleteClick(index){
      this.$confirm('确定删除 '+this.musicData[index].musicName+' ?', '删除音乐', {
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
    showModifyDialog(index){
      this.form=this.musicData[index];
      this.form.checked=(this.form.checked==1);
      this.dialogVisible=true;
    },
    modifyConfirm(){
      var form = new FormData();
      form.append("musicId", this.form.musicId);
      form.append("checked", this.form.checked?1:0);

      var settings = {
        "url": this.$backEndUrl+"admin/reviewMusic",
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
                  message: '审核音乐成功!'
                });
                this.getMusicData();
            }
            else
            {
                this.$message.error('审核音乐失败!');
            } 
      });

      this.dialogVisible = false;
    },
    deleteConfirm(index){
      var form = new FormData();
      form.append("musicId", this.musicData[index].musicId);

      var settings = {
        "url":  this.$backEndUrl+"admin/deleteMusic",
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
                  message: '删除音乐成功!'
                });
                this.getMusicData();
            }
            else
            {
                this.$message.error('删除音乐失败!');
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
.infoKey{
    color: #1E90FF;
    text-align: right;
}
.checkSwitch{
  padding: 15px;
}

</style>