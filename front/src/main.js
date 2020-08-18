// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'
// import mavonEditor from 'mavon-editor';
// import 'mavon-editor/dist/css/index.css';
// import 'iview/dist/styles/iview.css';
// import 'animate.css';

// 加载全局组件
import components from './components'
// 注册全局组件
Object.keys(components).forEach(key => {
  Vue.component(key, components[key])
})

Vue.prototype.documentTitle = '你的美好，我都记得'

// 以下配置 Valine 评论插件
// Register AV objects to the global
window.AV = require('leancloud-storage')

Vue.config.productionTip = false

// Vue.use(iView);
// Vue.use(mavonEditor);

// 设置英文
iView.lang('en-US')

router.beforeEach((to, from, next) => {
  let toRouteName = to.name
  let username = sessionStorage.getItem('username')
  let id = sessionStorage.getItem('id')
  if (toRouteName === 'login') {
    if (username !== null) {
      // 已登录，进入列表页
      store.dispatch('user/SETUSERID', +id)
      store.dispatch('user/SETUSERNAME', username)
      next({ name: 'list' })
    } else {
      next()
    }
  } else if (to.meta.requireAuth) {
    // 如果需要进入需登录的页面
    if (username !== null) {
      // 已登录，进入下一个页面
      store.dispatch('user/SETUSERID', +id)
      store.dispatch('user/SETUSERNAME', username)
      next()
    } else {
      // 否则进入登录页面
      next({
        name: 'login',
        query: { returnUrl: window.location.href }
      })
    }
  } else {
    next()
  }
})

router.afterEach((to, from) => {
  if (to.name === 'NotFound') {
    iView.LoadingBar.error()
  }
  if (to.meta && to.meta.title) {
    document.title = to.meta.cover ? to.meta.title : to.meta.title + ' - ' + Vue.prototype.documentTitle
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
