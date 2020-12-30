<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="0"/>
    <!-- 主体 -->
    <el-main>
        <h1 style="padding: 15vh; font-size:54px;">{{ msg }}</h1>
    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar'

export default {
  name: 'ErrorPage',
  components:{
    Navbar
  },
  data () {
    return {
      msg: '找不到页面！'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped>
  .el-main{
    background:linear-gradient(#f3f3f3,#fff);
  }
</style>
