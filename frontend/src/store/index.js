import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)


const store = new Vuex.Store({
  state:{
      sideBarCollapse:false,
      isLogin:localStorage.getItem('isLogin')?localStorage.getItem('isLogin'):false,
      uid:localStorage.getItem('uid')?localStorage.getItem('uid'):-1,
      adminLogin:localStorage.getItem('adminLogin')?localStorage.getItem('adminLogin'):false,

  }
})

export default store
