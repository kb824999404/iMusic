<template>
  <div class="Page">
    <el-card class="box" shadow="always">
      <el-row class="title">
        <h1>注册</h1>
      </el-row>
      <el-form label-width="0">
          <el-form-item>
              <el-input class="form-input" v-model="username" placeholder="用户名">
                  <font-awesome-icon slot="prefix" icon="user" />
              </el-input>
          </el-form-item>
          <el-form-item >
              <el-input class="form-input" v-model="password" placeholder="密码" show-password>
                  <font-awesome-icon slot="prefix" icon="lock" />
              </el-input>
          </el-form-item>
          <el-form-item >
              <el-input class="form-input" v-model="repassword" placeholder="确认密码" show-password>
                  <font-awesome-icon slot="prefix" icon="lock" />
              </el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" class="form-button" v-on:click="register" round>注册</el-button>
          </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      username: "",
      password: "",
      repassword: ""
    }
  },
  methods:{
        checkNumber: function(obj){
        var reg = /^[0-9]+$/;
        if (obj != "" && !reg.test(obj)) {
            return false;
        }
        return true;
    },
    chackForm: function () {
        var requestData = {
            username: this.username,
            password: this.password
        };
        for (var key in requestData) {
            if (requestData[key] == "") {
                alert("表单不完整，请重新填写！");
                return false;
            }
        }
        if (this.password != this.repassword) {
            alert("两次输入的密码不同，请重新填写！");
            return false;
        }
        if (this.password.length < 8) {
            alert("密码太短，请重新填写！");
            return false;
        }
        return true;
    },
    register: function () {              //注册
        var form = new FormData();
        form.append("username", this.username);
        form.append("password", this.password);

        var settings = {
        "url": this.$backEndUrl+"account/regesiter",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
        };

        this.$jquery.ajax(settings).done( (response)=> {
          var data=JSON.parse(response);
          console.log(data);
          if (data.status == 'true') {
              console.log('success');
              alert('注册成功！账号为：'+data.userId);
            this.$router.push({
                name: "login"
            });
          }
          else
          {
              console.log('fail');
              var message = data.message;
              alert(message);
          }        
        });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/account.css"></style>
<style scoped src="../styles/register.css"></style>
