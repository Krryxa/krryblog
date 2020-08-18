// import Vue from 'vue';
import Router from 'vue-router'

// Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: () => import('@/views/Home'),
      name: 'home',
      meta: { title: 'Krryblog' }
    },
    {
      path: '/page/:pageIndex(\\d+)',
      component: () => import('@/views/Home'),
      name: 'homePage',
      meta: { title: 'Krryblog' }
    },
    {
      path: '/2',
      redirect: '/link'
    },
    {
      path: '/3',
      redirect: '/about'
    },
    {
      path: '/:id(\\d+)',
      component: () => import('@/views/Blog'),
      name: 'blog'
    },
    {
      path: '/archive',
      component: () => import('@/views/Archive'),
      name: 'archive',
      meta: { title: '时间归档' }
    },
    {
      path: '/stars',
      component: () => import('@/views/Archive'),
      name: 'stars',
      meta: { title: '星茶会' }
    },
    {
      path: '/category/:id(\\d+)/:page?',
      component: () => import('@/views/Category'),
      name: 'category'
    },
    {
      path: '/tag/:name/:page?',
      component: () => import('@/views/Tag'),
      name: 'tag'
    },
    {
      path: '/about',
      component: () => import('@/views/About'),
      name: 'about',
      meta: { title: '关于我' }
    },
    {
      path: '/link',
      component: () => import('@/views/Link'),
      name: 'link',
      meta: { title: '友情链接' }
    },
    {
      path: '/search/:keyword/:page?',
      component: () => import('@/views/Search'),
      name: 'search'
    },
    {
      path: '/login/:returnUrl?',
      component: () => import('@/views/BackstageLogin'),
      name: 'login',
      meta: { title: '管理员登录', keepAlive: true }
    },
    {
      path: '/backstage/list/:page?',
      component: () => import('@/views/BackstageList'),
      name: 'list',
      meta: { title: '后台中心', requireAuth: true }
    },
    {
      path: '/backstage/music',
      component: () => import('@/views/BackstageMusic'),
      name: 'music',
      meta: { title: '音乐', requireAuth: true }
    },
    {
      path: '/backstage/edit/:id?',
      component: () => import('@/views/BackstageEdit'),
      name: 'edit',
      meta: { title: '新增博客', requireAuth: true }
    },
    {
      path: '/error',
      component: () => import('@/components/NotFound'),
      name: 'error',
      meta: { title: '404 - 找不到页面', cover: true }
    },
    {
      path: '*',
      component: () => import('@/components/NotFound'),
      name: 'NotFound',
      meta: { title: '404 - 找不到页面', cover: true }
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})
