'use strict'

import axios from 'axios'
import iView from 'iview'
import router from '@/router'
import { codeStatus } from '@/util/enum'

// 自动识别接口使用开发环境地址（开发环境地址做了 proxyTable 代理，故设置为空）或线上地址
const baseURL =
  process.env.NODE_ENV === 'production' ? process.env.API_ROOT : ''
// 开发环境直接打包测试
// axios.defaults.baseURL = '';

const $axios = axios.create({
  baseURL: baseURL,
  timeout: 20000,
  headers: { 'X-Requested-With': 'XMLHttpRequest' }
})

let flag = false

$axios.interceptors.request.use(
  config => {
    // 判断请求是否是 getClassify，如果是 getClassify，不加载 LoadingBar
    let url = config.url
    if (url.split('/').pop() === 'getClassify') {
      flag = false
    } else {
      iView.LoadingBar.start()
      flag = true
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

$axios.interceptors.response.use(
  res => {
    const apiRes = res.data
    switch (+apiRes.code) {
      case codeStatus.NOTFOUND:
        document.title = '404 - 找不到页面'
        flag && iView.LoadingBar.error()
        // 可以在这里直接设置跳转到 404 页面，已经不需要啦，在组件中已经设置，为了不改变 url，实现转发的效果
        // router.push({name: 'error'})
        break
      case codeStatus.UNAUTHORIZED:
        router.push({name: 'login'})
        flag && iView.LoadingBar.finish()
        break
      default:
        flag && iView.LoadingBar.finish()
        break
    }
    return apiRes
  },
  async error => {
    iView.LoadingBar.error()
    console.dir(error)
    return Promise.reject(error)
  }
)

export default $axios
