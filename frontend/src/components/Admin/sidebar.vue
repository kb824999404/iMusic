<template>
  <el-aside class="sidebar">
    <el-menu
      :default-active="activeIndex" :collapse="this.$store.state.sideBarCollapse">
      <el-menu-item index="1">
        <i class="el-icon-headset"></i>
        <a slot="title" :href="href[0]">音乐管理</a>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-user"></i>
        <a slot="title" :href="href[1]">音乐人管理</a>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <a slot="title" :href="href[2]">任务管理</a>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: 'Sidebar',
  computed:{
    activeIndex(){
        const route = this.$route
        const { meta, path } = route
        return meta.activeIndex
    }
  },
  data () {
    return {
        href:[
            location.origin+'/admin/music',
            location.origin+'/admin/musician',
            location.origin+'/admin/task',
        ]
    }
  },
  methods:{
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../../styles/Admin/sidebar.css"></style>