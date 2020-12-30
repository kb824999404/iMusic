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
          label-width="80px"
          class="infoForm"
        >

          <el-form-item label="任务描述" prop="description">
            <el-input type="textarea" v-model="description"></el-input>
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
          <el-form-item label="时长" prop="length">
            <el-select v-model="selectLength" placeholder="时长">
              <el-option
                v-for="item in Length"
                :key="item.id"
                :label="item.text"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit" :disabled="userId==0">发布任务</el-button>
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
  name: "PublishTask",
  components: {
    Navbar,
  },
  data() {
    return {
      msg: "发布任务",
      form:{},
      userId:0,

      title: "",
      nation: "",
      description: "",
      styles:[],
      instruments:[],
      scenes:[],
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


      rules: {
        description: [
          { required: true, message: "请输入任务描述", trigger: "change" },
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


    postForm(){
        var form = new FormData();

        form.append("userId", this.$store.state.uid);
        form.append("description", this.description);
        form.append("styleId",this.selectStyle);
        form.append("instrumentId", this.selectInstrument);
        form.append("sceneId", this.selectScene);
        form.append("length", this.selectLength);

        var settings = {
          "url": this.$backEndUrl+"task/publishTask",
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
        this.description == "" ||
        this.selectStyle == "" ||
        this.selectInstrument == "" ||
        this.selectScene == "" ||
        this.selectLength == ""
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
        this.$alert("您发布的任务信息以提交成功，请等待审核！", "提交成功", {
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
  },
  created(){
    if(this.$store.state.isLogin){
      this.userId=this.$store.state.uid;
      this.getAllStyle();
      this.getAllInstrument();
      this.getAllScene();
    }
    else
    {
      alert("请先登录！");
    }

  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/applymusician.css"></style>
