<template>
  <div class="panel">
    <el-table
      :data="musicianData"
      style="width: 100%">
      <el-table-column
        prop="musicianId"
        label="音乐人编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="userId"
        label="用户编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="musicianName"
        label="艺名"
        width="100">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        prop="countryId"
        label="国家"
        width="150">
        <template slot-scope="scope">
          <span>{{ countryMapper[scope.row.countryId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="favMusicStyle"
        label="喜欢的风格"
        width="100">
        <template slot-scope="scope">
          <span>{{ styleMapper[scope.row.favMusicStyle]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="favInstrument"
        label="擅长的乐器"
        width="100">
        <template slot-scope="scope">
          <span>{{ instrumentMapper[scope.row.favInstrument]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="认证状态"
        width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.checked==1?"已认证":"未认证" }}</span>
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

    <!-- 审核音乐人对话框 -->
    <el-dialog title="审核音乐" :visible.sync="dialogVisible" width="30%">
      <el-row>
        <el-col :span="8"><p class="infoKey">音乐人编号：</p></el-col>
        <el-col :span="16"><p>{{form.musicianId}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">用户编号：</p></el-col>
        <el-col :span="16"><p>{{form.userId}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">艺名：</p></el-col>
        <el-col :span="16"><p>{{form.musicianName}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">国家：</p></el-col>
        <el-col :span="16"><p>{{countryMapper[form.countryId]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">描述：</p></el-col>
        <el-col :span="16"><p>{{form.description}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">喜欢的风格：</p></el-col>
        <el-col :span="16"><p>{{styleMapper[form.favMusicStyle]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">擅长乐器：</p></el-col>
        <el-col :span="16"><p>{{instrumentMapper[form.favInstrument]}}</p></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">附件：</p></el-col>
        <el-col :span="16"><el-link 
        :href="this.$backEndUrl+'musicianAppendix/'+form.appendix"><p>查看附件</p></el-link></el-col>
      </el-row>
      <el-row>
        <el-col :span="8"><p class="infoKey">认证状态：</p></el-col>
        <el-col :span="16">
          <el-switch class="checkSwitch"
            v-model="form.checked"
            active-text="已认证"
            inactive-text="未认证">
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
  name: 'MusicianManagement',

  components:{
  },
  data () {
    return {
      msg: '音乐人管理',
      musicianData:[],
      dialogVisible:false,
      form: {
      },
      Style:[],
      Instrument:[],
      Country:[],
      styleMapper:{},
      instrumentMapper:{},
      countryMapper:{},
      getDataStatus:[false,false,false]
    }
  },
  methods:{
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2])
        this.getMusicianData();
    },
    getMusicianData(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"admin/getAllMusician",
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
                  this.musicianData=data.musicians;
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
    showModifyDialog(index){
      this.form=this.musicianData[index];
      this.form.checked=(this.form.checked==1);
      this.dialogVisible=true;
    },
    onDeleteClick(index){
      this.$confirm('确定删除 '+this.musicianData[index].musicianName+' ?', '删除音乐人', {
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
    modifyConfirm(){
      var form = new FormData();
      form.append("musicianId", this.form.musicianId);
      form.append("checked", this.form.checked?1:0);

      var settings = {
        "url": this.$backEndUrl+"admin/reviewMusician",
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
                  message: '审核音乐人成功!'
                });
                this.getMusicianData();
            }
            else
            {
                this.$message.error('审核音乐人失败!');
            } 
      });

      this.dialogVisible = false;
    },
    deleteConfirm(index){
      var form = new FormData();
      form.append("musicianId", this.musicianData[index].musicianId);

      var settings = {
        "url":  this.$backEndUrl+"admin/deleteMusician",
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
                  message: '删除音乐人成功!'
                });
                this.getMusicianData();
            }
            else
            {
                this.$message.error('删除音乐人失败!');
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
            if (data.status == 'true') {
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
            if (data.status == 'true') {
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
    this.getAllCountry();
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