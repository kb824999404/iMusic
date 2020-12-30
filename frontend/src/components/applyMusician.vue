<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0" />
    <!-- 主体 -->
    <el-main>
      <el-container class="applyInfo">
        <el-header>
          <h2>{{ msg }}</h2>
        </el-header>
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="120px"
          class="infoForm"
        >
          <el-form-item label="艺名" prop="name">
            <el-input v-model="name"></el-input>
          </el-form-item>
          <el-form-item label="国籍" prop="country">
            <el-select
              placeholder="请选择国籍"
              filterable
              v-model="nation"
            >
                <el-option
                  v-for="item in countrys"
                  :key="item.countryId"
                  :label="item.countryName"
                  :value="item.countryId">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="个人描述" prop="description">
            <el-input type="textarea" v-model="description"></el-input>
          </el-form-item>

          <el-form-item label="喜欢的音乐风格" prop="style">
              <el-select v-model="selectStyle" placeholder="请选择音乐风格">
                <el-option
                  v-for="item in styles"
                  :key="item.styleId"
                  :label="item.styleName"
                  :value="item.styleId">
                </el-option>
              </el-select>
          </el-form-item>

          <el-form-item label="擅长的乐器" prop="instrument">
              <el-select v-model="selectInstrument" placeholder="请选择乐器">
                <el-option
                  v-for="item in instruments"
                  :key="item.instrumentId"
                  :label="item.instrumentName"
                  :value="item.instrumentId">
                </el-option>
              </el-select>
          </el-form-item>
          <el-form-item label="附件">
            <el-upload
              class="upload-demo"
              multiple
              :limit="1"
              :action="this.$backEndUrl+'musician/uploadAppendix'"
              :on-exceed="handleAppendixExceed"
              :on-success="uploadAppendixSuccess"
              :on-remove="handleAppendixRemove"
            >
              <el-button size="small" type="primary" :disabled="this.musicianId==0">点击上传文件</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit">成为音乐人</el-button>
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
  name: "ApplyMusician",
  components: {
    Navbar,
  },
  data() {
    return {
      msg: "申请音乐人认证",
      userId:-1,

      name: "",
      nation: "",
      description: "",

      styles:[],
      instruments:[],
      countrys:[],

      selectStyle:"",
      selectInstrument:"",

      appendixUrl:"",
      uploadAppendixStatus:false,

      rules: {
        name: [{ required: true, message: '请输入艺名', trigger: 'change' },
            { min: 0, max: 20, message: '长度不大于10个字', trigger: 'blur' }],
        country: [{ required: true, message: "请选择国籍", trigger: "blur" }],
        description: [
          { required: true, message: "请输入个人描述", trigger: "change" },
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
                this.countrys=data.countrys;
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
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
        form.append("userId", this.userId);
        form.append("musicianName", this.name);
        form.append("description", this.description);
        form.append("countryId", this.nation);
        form.append("favMusicStyle", this.selectStyle);
        form.append("favInstrument", this.selectInstrument);
        form.append("appendixUrl", this.appendixUrl);

        var settings = {
          "url": this.$backEndUrl+"musician/applyMusician",
          "method": "POST",
          "timeout": 0,
          "processData": false,
          "mimeType": "multipart/form-data",
          "contentType": false,
          "data": form
        };

        this.$jquery.ajax(settings).done((response) =>{
          var data=JSON.parse(response);
          console.log(data);
          if (data.status === 'true') {
              console.log('success');
              this.$alert("您的音乐人信息以提交成功，请等待审核！", "提交成功", {
                confirmButtonText: "确定",
                callback: (action) => {
                  this.$message({
                    type: "success",
                    message: "申请提交成功!",
                  });
                },
              });
          }
          else
          {
              console.log('fail');
              var message = data.message;
              this.$message.error(message);
          }
        });
    },

    onSubmit() {
      if (
        this.name == "" ||
        this.description == "" ||
        this.country == "" ||
        !this.uploadAppendixStatus
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
    if(this.$store.state.isLogin==="true"){
      this.userId=this.$store.state.uid;
      this.getAllStyle();
      this.getAllInstrument();
      this.getAllCountry();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/applymusician.css"></style>
