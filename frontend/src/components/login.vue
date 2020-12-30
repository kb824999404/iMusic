<template>
  <div class="Page">
    <el-card class="box" shadow="always">
      <el-row class="title">
        <h1>登录</h1>
      </el-row>
      <el-form label-width="0">
          <el-form-item>
              <el-input class="form-input" v-model="userid" placeholder="账号">
                  <font-awesome-icon slot="prefix" icon="user" />
              </el-input>
          </el-form-item>
          <el-form-item >
              <el-input class="form-input" v-model="password" placeholder="密码" show-password>
                  <font-awesome-icon slot="prefix" icon="lock" />
              </el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" class="form-button" v-on:click="login" round>登录</el-button>
          </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      userid: '',
      password: ''
    }
  },
  methods: {
    checkNumber: function (obj) {
        var reg = /^[0-9]+$/;
        if (obj != "" && !reg.test(obj)) {
            return false;
        }
        return true;
    },
    login: function () {            //登录
        var userid = this.userid;
        var password = this.password;
        if (!this.checkNumber(userid)) {
            alert("账号格式错误！");
            return;
        }
        if (userid=="") {
            alert("账号不能为空！");
            return;
        }
        if (password == "") {
            alert("密码不能为空！");
            return;
        }
        var form = new FormData();
        form.append("userId", userid);
        form.append("password", password);

        var settings = {
        "url": this.$backEndUrl+"account/login",
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
                console.log('login success');
                localStorage.setItem('isLogin',true);
                localStorage.setItem('uid',userid);
                this.$store.state.isLogin=true;
                this.$store.state.uid=userid;
                this.$router.push({
                    name: "home"
                });
                location.reload();
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
<style scoped src="../styles/login.css"></style>
