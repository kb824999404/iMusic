<template>
  <div class="Page">
    <el-card class="box" shadow="always">
      <el-row class="title">
        <h1>管理员登录</h1>
      </el-row>
      <el-form label-width="0">
          <el-form-item >
              <el-input class="form-input" v-model="password" placeholder="请输入管理员密码" show-password>
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
  name: 'AdminLogin',
  data () {
    return {
      password: ''
    }
  },
  methods: {
    login: function () {            //登录
        var form = new FormData();
        form.append("password", this.password);

        var settings = {
          "url": this.$backEndUrl+"admin/login",
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
                localStorage.setItem('adminLogin',true);
                this.$store.state.adminLogin=true;
                this.$router.push({
                    name: "musicManagement"
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
<style scoped src="../../styles/account.css"></style>
<style scoped src="../../styles/login.css"></style>
