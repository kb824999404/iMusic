<template>
  <el-row>
    <el-container>
      <!-- 侧边栏 -->
      <Sidebar/>
      <!-- 主体 -->
      <el-main>
          <Navbar />
          <router-view/>
      </el-main>
    </el-container>
  </el-row>
</template>

<script>
import Sidebar from '@/components/Admin/sidebar'
import Navbar from '@/components/Admin/navbar'

export default {
  name: 'AdminLayout',
  components:{
    Sidebar,
    Navbar
  },
  data () {
    return {
    }
  },
  methods:{
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .el-main{
    padding: 0;
    height:100vh;
  }
</style>