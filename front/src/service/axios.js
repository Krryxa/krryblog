'use strict';

import axios from 'axios';

// 自动识别接口使用开发环境地址（开发环境地址做了 proxyTable 代理，故设置为空）或线上地址
axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? process.env.API_ROOT : '';
// 开发环境直接打包测试
// axios.defaults.baseURL = '';

axios.interceptors.request.use(config => {
  return config;
}, error => {
  console.log(error);
  return Promise.reject(error);
});

axios.interceptors.response.use(res => {
  const apiRes = res.data;
  if (apiRes.status === 404) {
    document.title = '404 - 找不到页面';
    // 可以在这里直接设置跳转到 404 页面，已经不需要啦，在组件中已经设置
    // router.push({name: 'NotFound'});
  }
  return apiRes;
}, async error => {
  console.dir(error);
  return Promise.reject(error);
});

export default axios;
