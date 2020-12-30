<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0" />
    <!-- 主体 -->
    <el-main>
      <el-container class="publishInfo">
        <el-header>
          <h2>{{ msg }}</h2>
        </el-header>
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="80px"
          class="infoForm"
        >
          <el-form-item label="音频" prop="file">
            <el-upload
              class="upload-demo"
              :action="this.$backEndUrl+'music/uploadMusic'"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :on-success="uploadSuccess"
              :on-remove="handleRemove"
              :file-list="fileList"
            >
              <el-button size="small" type="primary" :disabled="this.musicianId==0">点击上传音频</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="歌名" prop="name">
            <el-input v-model="name"></el-input>
          </el-form-item>
          <el-form-item label="歌曲描述" prop="description">
            <el-input type="textarea" v-model="description"></el-input>
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input-number v-model="price" :min="0" label="" :step="1"></el-input-number>
          </el-form-item>
          <el-form-item label="音乐风格" prop="style">
              <el-select v-model="selectStyle" placeholder="请选择音乐风格">
                <el-option
                  v-for="item in styles"
                  :key="item.styleId"
                  :label="item.styleName"
                  :value="item.styleId">
                </el-option>
              </el-select>
          </el-form-item>

          <el-form-item label="乐器" prop="instrument">
              <el-select v-model="selectInstrument" placeholder="请选择乐器">
                <el-option
                  v-for="item in instruments"
                  :key="item.instrumentId"
                  :label="item.instrumentName"
                  :value="item.instrumentId">
                </el-option>
              </el-select>
          </el-form-item>

          <el-form-item label="场景" prop="scene">
              <el-select v-model="selectScene" placeholder="请选择场景">
                <el-option
                  v-for="item in scenes"
                  :key="item.sceneId"
                  :label="item.sceneName"
                  :value="item.sceneId">
                </el-option>
              </el-select>
          </el-form-item>

          <el-form-item label="时长(秒)" prop="length">
            <el-input-number v-model="musicLength" :min="0" label="" :step="1"></el-input-number>
          </el-form-item>
          <el-form-item label="附件">
            <el-upload
              class="upload-demo"
              multiple
              :limit="1"
              :action="this.$backEndUrl+'music/uploadAppendix'"
              :on-exceed="handleAppendixExceed"
              :on-success="uploadAppendixSuccess"
              :on-remove="handleAppendixRemove"
            >
              <el-button size="small" type="primary" :disabled="this.musicianId==0">点击上传文件</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit" :disabled="this.musicianId==0">发布音乐</el-button>
          </el-form-item>
        </el-form>
      </el-container>

      <!-- 回到顶部按钮 -->
      <el-backtop> </el-backtop>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from "@/components/navbar";

export default {
  name: "PublishMusic",
  components: {
    Navbar,
  },
  data() {
    return {
      msg: "发布音乐",
      form:{
        name:'',
        description:'',
        price:0,
        style:0
      },
      musicianId:0,
      name: "",
      description: "",
      price:0,
      musicLength:0,

      styles:[],
      instruments:[],
      scenes:[],

      selectStyle:"",
      selectInstrument:"",
      selectScene:"",

      fileList: [],
      fileUrl:"",
      uploadStatus:false,
      appendixUrl:"",
      uploadAppendixStatus:false,

      rules: {
        file: [{ required: true, message: '请上传音频', trigger: 'change' }],
        name: [{ required: true, message: '请输入歌名', trigger: 'change' },
            { min: 0, max: 200, message: '长度不大于100个字', trigger: 'blur' }],
        description: [
          { required: true, message: "请输入歌曲描述", trigger: "change" },
          { min: 0, max: 400, message: "长度在200字以内", trigger: "blur" },
        ],
        style: [
          {
            type: "tag",
            required: true,
            message: "请选择音乐风格",
            trigger: "change",
          },
        ],
      },
    };
  },
  methods: {
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
                this.styles=data.styles;
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
                this.instruments=data.instruments;

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
            if (data.status == 'true') {
                console.log('success');
                this.scenes=data.scenes;
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },

    handleExceed() {
      this.$message.warning(
        `一次只能上传一个音频！`
      );
    },
    handleRemove(){
      this.uploadStatus=false;
    },
    uploadSuccess(response, file, fileList){
        var data=response;
        console.log(data);
        if (data.status == 'true') {
            console.log('success');
            this.fileUrl=data.fileUrl;
            this.uploadStatus=true;
        }
        else
        {
            console.log('fail');
            var message = data.message;
            alert(message);
        }
    },
    handleAppendixExceed() {
      this.$message.warning(
        `一次只能上传一个附件！`
      );
    },
    handleAppendixRemove(){
      this.uploadAppendixStatus=false;
    },
    uploadAppendixSuccess(response, file, fileList){
        var data=response;
        console.log(data);
        if (data.status == 'true') {
            console.log('success');
            this.appendixUrl=data.fileUrl;
            this.uploadAppendixStatus=true;
        }
        else
        {
            console.log('fail');
            var message = data.message;
            alert(message);
        }
    },
    postForm(){
      var form = new FormData();
      form.append("musicianId", this.musicianId);
      form.append("musicName", this.name);
      form.append("description", this.description);
      form.append("styleId",this.selectStyle);
      form.append("instrumentId", this.selectInstrument);
      form.append("sceneId", this.selectScene);
      form.append("length", this.musicLength);
      form.append("price", this.price);
      form.append("fileUrl", this.fileUrl);
      form.append("appendixUrl", this.appendixUrl);

      var settings = {
        "url": this.$backEndUrl+"music/publishMusic",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done(function (response) {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              this.fileUrl=data.fileUrl;
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }
      });
    },
    onSubmit() {
      if (
        this.name == "" ||
        this.description == "" ||
        !this.uploadStatus ||
        !this.uploadAppendixStatus ||
        this.selectStyle == "" ||
        this.selectInstrument == "" ||
        this.selectScene == ""
      ) {
        this.$alert("请完善表单信息！", "无法提交", {
          confirmButtonText: "确定",
          callback: (action) => {
            this.$message({
              type: "alert",
              message: "请完善表单信息！",
            });
          },
        });
      } else {
        this.postForm();
        this.$alert("您的音乐人信息以提交成功，请等待审核！", "提交成功", {
          confirmButtonText: "确定",
          callback: (action) => {
            this.$message({
              type: "success",
              message: "申请提交成功!",
            });
          },
        });

        this.$refs["form"].validate((valid) => {
          if (valid) {
            alert("submit!");
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      }
    },
  },
  created(){
    if(this.$route.params.musicianId)
    {
      this.musicianId=this.$route.params.musicianId;
      this.getAllStyle();
      this.getAllInstrument();
      this.getAllScene();
    }
    else
    {
      alert("您还不是音乐人！");
    }

  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/publishmusic.css"></style>
