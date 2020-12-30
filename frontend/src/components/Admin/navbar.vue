<template>
    <div class="navbar">
      <font-awesome-icon class="collapse-icon" icon="bars" @click="setCollapse"/>
      <p>{{routeName}}</p>
      <div class="navbar-item-blank-center"></div>
      <el-dropdown>
        <span class="dropdown-link">
          <img src="../../assets/avatar.jpg" class="avatar"/>
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="tohome"><a >iMusic</a></el-dropdown-item>
          <el-dropdown-item @click.native="logout"><a >登出</a></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
</template>

<script>
export default {
  name: 'Navbar',
  computed:{
    routeName(){
        const route = this.$route
        const { meta, path } = route
        return meta.name
    }
  },
  data () {
    return {

    }
  },
  methods:{
    setCollapse(){
      this.$store.state.sideBarCollapse=!this.$store.state.sideBarCollapse;
    },
    logout(){
        console.log("HEllo");
        localStorage.setItem('adminLogin',false);
        this.$store.state.adminLogin=false;
        this.$router.push({
            name: "adminLogin"
        });
        location.reload();
    },
    tohome(){
        this.$router.push({
            name: "home"
        });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../../styles/Admin/navbar.css"></style>