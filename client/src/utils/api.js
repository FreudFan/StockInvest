import axios from 'axios'
import {Message} from 'element-ui'

// 封装axios
/*
 例子：
  var _this = this;
  this.loading = true;
  this.postRequest('/login', {
    username: this.loginForm.username,
    password: this.loginForm.password
  }).then(resp=> {
    _this.loading = false;
    if (resp && resp.status == 200) {
      _this.getRequest("/config/hr").then(resp=> {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.$store.commit('login', data);
          var path = _this.$route.query.redirect;
          _this.$router.replace({path: path == '/' || path == undefined ? '/home' : path});
        }
      })
    }
  });
*/

let _this = this;

axios.interceptors.request.use(config=> {
  return config;
}, err=> {
  Message.error({message: '请求超时! 请重新登录'});
  return Promise.resolve(err);
})
axios.interceptors.response.use(data=> {
  if (data.status && data.status == 200 && data.data.status == 'error') {
    Message.error({message: data.data.msg});
    return;
  }
  return data;
}, err=> {
  if (err.response.status == 504||err.response.status == 404) {
    Message.error({message: '服务器被吃了⊙﹏⊙∥'});
  } else if (err.response.status == 401) {
    Message.error({message: '权限不足,请联系管理员!'});
  }else {
    Message.error({message: '未知错误!'});
  }
  return Promise.resolve(err);
})

let base = '';

export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    /*
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
    */
  });
}

export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}

export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

export const deleteRequest = (url) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`
  });
}

export const getRequest = (url) => {
  return axios({
    method: 'get',
    url: `${base}${url}`
  });
}
