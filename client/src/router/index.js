import Vue from 'vue'
import Router from 'vue-router'
import Register from "@/views/register/Register";
import Login from "@/views/login/Login";
import Home from "@/views/Home";
import SysMenu from "../views/system/SysMenu";
import EmpManage from "@/views/employee/EmpManage";
Vue.use(Router)

export default new Router({
  // mode: 'history',
  routes: [
    {
      path:'/',
      name: '登录',
      component: Login,
      hidden: true
    },
    {
      path:'/register',
      name: '注册',
      component: Register,
      hidden: true
    },
    // {
    //   path:'/home',
    //   name: '主页',
    //   component: Home,
    //   meta: {
    //     requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
    //   },
    // },
    {
      path:'/EmpManage',
      name: '用户管理1',
      component: EmpManage,
      hidden: true
    },
  ]
})
