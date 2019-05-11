// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/index'
import ElementUI, {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Axios from 'axios';
import store from './store'
import qs from 'qs'
import {deleteRequest, getRequest, postRequest, putRequest} from "@/utils/api";
import {initMenu} from "@/utils/utils";
import 'font-awesome/css/font-awesome.min.css'

Vue.config.productionTip = false;
Vue.prototype.$axios = Axios;
Vue.prototype.$qs = qs;
Vue.use(ElementUI);

Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.putRequest = putRequest;

// Axios.defaults.timeout = 5000;
Axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
Vue.prototype.changeWinSize = changeWinSize;


router.beforeEach((to, from, next)=> {
    if ( to.name == '登录' || to.name == '注册' ) {
      next();
      return;
    }
    let name = store.state.user.name;
    if (name == '未登录') {
      Message.success({message: '欢迎访问投研系统，请登录！'});
      if (to.meta.requireAuth || to.name == null) {
        next({path: '/', query: {redirect: to.path}})
      } else {
        next();
      }
    } else {
      initMenu(router, store);
      next();
    }
  }
)


//自定义提示框
Vue.prototype.$elMessage = function (msg,t) {
  this.$message({
    message: msg,
    type: t   // success || warning || error
  })
};

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>',
  mounted() {
    changeWinSize();
  }
});

window.onload = function () {
  changeWinSize();
};

window.onresize = function () {
  changeWinSize();
};

function changeWinSize() {
  let winHeight = document.body.clientHeight;
  let asideDiv = document.getElementById("app-aside");
  if ( asideDiv != null ) {
    let menuHeight = winHeight - 50;
    document.getElementById("app-aside").style.cssText = 'height: ' + menuHeight + "px !important";
  }
  document.getElementById("app").style.cssText = 'height: ' + winHeight + "px !important";
}
