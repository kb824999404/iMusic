import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import Login from '@/components/login'
import Register from '@/components/register'
import Store from '@/components/store'
import Musician from '@/components/musician'
import Task from '@/components/task'
import MyPage from '@/components/mypage'
import MusicInfo from '@/components/musicInfo'
import MusicianInfo from '@/components/musicianInfo'
import ApplyMusician from '@/components/applyMusician'
import PublishMusic from '@/components/publishMusic'
import PublishTask from '@/components/publishTask'
import TaskInfo from '@/components/taskInfo'
import Message from '@/components/message'
import ErrorPage from '@/components/error'
import Blank from '@/components/blank'

import AdminLayout from '@/components/Admin/layout'

Vue.use(Router)

var router=new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        title: 'iMusic —— 首页'
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {
        title: 'iMusic —— 登录'
      }
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: {
        title: 'iMusic —— 注册'
      }
    },
    {
      path: '/store',
      name: 'store',
      component: Store,
      meta: {
        title: 'iMusic —— 音乐商店'
      }
    },
    {
      path: '/musician',
      name: 'musician',
      component: Musician,
      meta: {
        title: 'iMusic —— 音乐人'
      }
    },
    {
      path: '/task',
      name: 'task',
      component: Task,
      meta: {
        title: 'iMusic —— 任务广场'
      }
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPage,
      meta: {
        title: 'iMusic —— 个人主页'
      }
    },
    {
      path: '/musicInfo',
      name: 'musicInfo',
      component: MusicInfo,
      meta: {
        title: 'iMusic —— 音乐信息'
      }
    },
    {
      path: '/musicianInfo',
      name: 'musicianInfo',
      component: MusicianInfo,
      meta: {
        title: 'iMusic —— 音乐人信息'
      }
    },
    {
      path: '/applyMusician',
      name: 'applyMusician',
      component: ApplyMusician,
      meta: {
        title: 'iMusic —— 音乐人认证'
      }
    },
    {
      path: '/publishMusic',
      name: 'publishMusic',
      component: PublishMusic,
      meta: {
        title: 'iMusic —— 发布音乐'
      }
    },
    {
      path: '/publishTask',
      name: 'publishTask',
      component: PublishTask,
      meta: {
        title: 'iMusic —— 发布任务'
      }
    },
    {
      path: '/taskInfo',
      name: 'taskInfo',
      component: TaskInfo,
      meta: {
        title: 'iMusic —— 任务信息'
      }
    },
    {
      path: '/message',
      name: 'message',
      component: Message,
      meta: {
        title: 'iMusic —— 私信'
      }
    },
    {
      path: '/error',
      name: 'error',
      component: ErrorPage,
      meta: {
        title: 'iMusic —— 找不到页面'
      }
    },
    {
      path: '/admin',
      name:'admin',
      redirect: '/admin/music',
      component:AdminLayout,
      children:[
        {
          path:'music',
          name:'musicManagement',
          component:() => import('@/components/Admin/music'),
          meta: {
            title: 'iMusic —— 音乐管理',
            activeIndex: '1',
            name:'音乐管理'
          }
        },
        {
          path:'musician',
          name:'musicianManagement',
          component:() => import('@/components/Admin/musician'),
          meta: {
            title: 'iMusic —— 音乐人管理',
            activeIndex: '2',
            name:'音乐人管理'
          }
        },
        {
          path:'/admin/task',
          name:'taskManagement',
          component:() => import('@/components/Admin/task'),
          meta: {
            title: 'iMusic —— 任务管理',
            activeIndex: '3',
            name:'任务管理'
          }
        }
      ]

    },
    {
      path:'/admin/login',
      name:'adminLogin',
      component:() => import('@/components/Admin/login'),
      meta: {
        title: 'iMusic —— 管理员登录'
      }
    },
    {
      path: '/blank',
      name: 'blank',
      component: Blank,
      meta: {
        title: 'iMusic —— 空白页'
      }
    },
    {
      path: '*',
      redirect: '/error'
    }
  ]
});

var adminPages=['musicManagement','musicianManagement','taskManagement'];

router.beforeEach((to, from, next) => {
  var isLogin=localStorage.getItem("adminLogin")?localStorage.getItem("adminLogin"):'false';
  isLogin=(isLogin==='true')?true:false;
  if(to.path == "/admin/login"){
    if(isLogin){
      next("/admin");
    }else{
      next();
    }
  }else{
    var isAdmin=false;
    for(var i=0;i<adminPages.length;i++){
      if(to.name===adminPages[i]){
        isAdmin=true;
        break;
      }
    }
    if(isAdmin){
      if(isLogin){
        next();
      }
      else
      {
        next("/admin/login");
      }
    }
    else{
      next();
    }
  }
});

export default router;
