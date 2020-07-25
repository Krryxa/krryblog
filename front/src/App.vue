<template>
  <div id="app">
    <my-header v-if="$route.name !== 'edit'"></my-header>
    <!-- 需要缓存走这里 -->
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <!-- 正常访问走下面，不需要缓存 -->
    <router-view v-if="!$route.meta.keepAlive"></router-view>
    <music></music>
    <my-footer></my-footer>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import '@/util/mouseHeart'
export default {
  name: 'App',
  components: {
    Music: () => import('@/components/Music')
  },
  data() {
    return {}
  },
  created() {
    this.getClassifyList()
  },
  methods: {
    // 将 `this.setClassify()` 映射为 `this.$store.dispatch('blog/SETCLASSIFY')`
    ...mapActions({
      setClassify: 'blog/SETCLASSIFY'
    }),
    getClassifyList() {
      // 调用 vuex 中的 action 保存分类信息
      this.setClassify()
    }
  }
}
</script>

<style>
html,
body {
  height: 100%;
  cursor: url(./assets/pic/pointer.cur), default;
}
a,
button {
  cursor: url(./assets/pic/cursor.cur), pointer !important;
}
a {
  color: #666;
}
a:hover {
  color: #eb5055 !important;
  transition: 0.3s;
}
ul li {
  list-style: none;
}
.right {
  float: right;
}
.left {
  float: left;
}
img {
  vertical-align: middle;
}
.clear {
  clear: both;
}
.animated {
  animation-duration: 0.5s;
}

.heart {
  animation: 0.7s heartMove ease infinite alternate;
}

@keyframes heartMove {
  50% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(90deg);
  }
}

/* 遮罩层 */
.ivu-modal-mask,
.ivu-modal-wrap {
  z-index: 1010 !important;
}

/* message 提示信息 */
.ivu-message {
  z-index: 1011 !important;
}

/* 表单的验证信息右移 */
.ivu-form-item-error-tip {
  left: unset;
  right: 0;
}

/* 代码字体 */
.hljs {
  padding: 18px 15px 12px !important;
}
.hljs-name {
  font-weight: bold !important;
}
.hljs-name, .hljs-tag {
  color: #333 !important;
}
.hljs-keyword {
  color: rgb(153, 6, 154) !important;
}
.hljs-comment,
.hljs-quote {
  font-style: normal !important;
  font-weight: bold;
}

/* 回到顶部 */
.ivu-back-top {
  z-index: 1009;
  display: block;
  visibility: hidden;
  opacity: 0;
  bottom: 0 !important;
  transition: 0.4s ease;
  right: unset !important;
  margin-left: 1020px;
  cursor: url(./assets/pic/cursor.cur), pointer !important;
}
.ivu-back-top.ivu-back-top-show {
  transform: rotate(360deg);
  bottom: 60px !important;
  opacity: 1;
  visibility: visible;
}
.ivu-back-top i {
  padding: 8px 8px;
}
.ivu-back-top-inner {
  background: rgba(255, 152, 49, 0.8);
  border-radius: 50%;
  height: 40px;
  width: 40px;
}
.ivu-back-top-inner:hover {
  background: rgba(255, 111, 27, 0.9) !important;
}

/* 开关按钮 */
.ivu-switch {
  cursor: url(./assets/pic/cursor.cur), pointer !important;
}
.ivu-switch::after {
  cursor: url(./assets/pic/cursor.cur), pointer !important;
}

/* 加载动画 */
.icon-load {
  animation: ani-demo-spin 1s linear infinite;
}

/* 分页样式的冲突和解决 */
.ivu-page {
  text-align: center;
  margin: 36px 0 auto;
  font-size: 14px;
}
.ivu-page li {
  cursor: url(./assets/pic/cursor.cur), pointer !important;
}

.ivu-page li:not(.ivu-page-disabled):hover a {
  color: #eb5055;
}
.ivu-page-item-active a {
  color: #eb5055;
}

.ivu-page li.ivu-page-disabled a {
  cursor: not-allowed !important;
}

/* 滚动条优化 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
::-webkit-scrollbar-track {
  background-color: transparent;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
::-webkit-scrollbar-track-piece {
  background: #fff;
}
::-webkit-scrollbar-thumb {
  background-color: #ff8b18;
  background-image: -webkit-linear-gradient(
    45deg,
    rgba(255, 255, 255, 0.4) 25%,
    transparent 25%,
    transparent 50%,
    rgba(255, 255, 255, 0.4) 50%,
    rgba(255, 255, 255, 0.4) 75%,
    transparent 75%,
    transparent
  );
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
::-webkit-scrollbar-thumb:hover {
  cursor: url(./assets/pic/cursor.cur), pointer;
  background-color: #ff4f00;
}
::-webkit-scrollbar-corner {
  background: #535353;
}
::-webkit-scrollbar-resizer {
  background: #ff0bee;
}
scrollbar {
  -moz-appearance: none !important;
  background: rgb(0, 255, 0) !important;
}
scrollbarbutton {
  -moz-appearance: none !important;
  background-color: rgb(0, 0, 255) !important;
}
scrollbarbutton:hover {
  -moz-appearance: none !important;
  background-color: rgb(255, 0, 0) !important;
}
/* 隐藏上下箭头 */
scrollbarbutton {
  display: none !important;
}
/* 纵向滚动条宽度 */
scrollbar[orient='vertical'] {
  min-width: 12px !important;
}
::selection {
  background: #ff5252;
  color: #fff;
}

/* 响应式 */
@media screen and (max-width: 1080px) {
  /* 回到顶部 */
  .ivu-back-top {
    right: 30px !important;
  }
}

@media screen and (max-width: 967px) {
  /* 每行显示两个博客 */
  .section-article {
    width: 680px;
  }
  .section-article article {
    margin-left: 30px !important;
    margin-right: 30px !important;
  }
}

@media screen and (max-width: 780px) {
  /* 每行显示两个博客时，中间空格减小 */
  .section-article {
    width: 640px;
  }
  .section-article article {
    margin-left: 20px !important;
    margin-right: 20px !important;
  }
}

@media screen and (max-width: 647px) {
  /* 每行显示一个博客，大小根据屏幕变化 */
  .section-article {
    width: 100%;
  }
  .section-article article {
    width: 70% !important;
    margin-left: 15% !important;
    margin-right: 15% !important;
  }
  /* 底部响应式 */
  footer .container .link-git {
    position: absolute !important;
    top: 40px !important;
    left: 0;
    right: 0;
    margin: auto;
  }
  footer .container .footer-flot {
    top: 64% !important;
  }
  footer .container .sec-footer-flot {
    display: none !important;
  }
  /* 汇总数据分行 */
  article .summarize ul li {
    width: 50% !important;
  }
}

@media screen and (max-width: 408px) {
  /* 每行显示一个博客，左右外边框变小 */
  .section-article {
    width: 100%;
  }
  .section-article article {
    width: 80% !important;
    margin-left: 10% !important;
    margin-right: 10% !important;
  }
}

@media screen and (max-width: 330px) {
  /* 每行显示一个博客，左右外边框再次变小 */
  .section-article {
    width: 100%;
  }
  .section-article article {
    width: 86% !important;
    margin-left: 7% !important;
    margin-right: 7% !important;
  }
}

@media screen and (max-width: 900px) {
  /* 目录隐藏 */
  #directory {
    display: none;
  }
}

@media screen and (max-width: 600px) {
  /* 头部菜单收起 */
  .head-page .home {
    float: none;
    text-align: center;
  }
  .head-page .nav-coll-menu {
    display: block !important;
  }
  .head-page .nav {
    position: absolute !important;
    top: 0;
    right: 6px;
    margin-left: 6px;
  }
  .head-page .nav .nav-far {
    visibility: hidden;
  }
  .head-page .nav .search i {
    font-size: 26px !important;
    height: 34px !important;
    width: 34px !important;
    top: 18px !important;
    line-height: 34px !important;
    z-index: 2 !important;
  }
  .head-page .nav .search input {
    height: 34px !important;
    top: 18px !important;
    z-index: 1;
  }
}

@media screen and (max-width: 460px) {
  /* 时间隐藏 */
  .detail-article .art-header .tag-time {
    display: none;
  }
  .detail-article .art-header .header-tag {
    width: 100% !important;
  }
}

@media screen and (max-width: 370px) {
  /* 底部响应式 */
  footer .container .fir-desc-footer {
    position: absolute !important;
    top: 54% !important;
    left: 0;
    right: 0;
  }
  footer .container .fir-footer-flot {
    display: none !important;
  }
  footer .container .sec-desc-footer {
    top: 78% !important;
  }
  footer .container .link-git {
    top: 30% !important;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    margin-top: -20px;
  }
  to {
    opacity: 1;
    margin-top: 0;
  }
}
</style>

<style lang="scss">
.ivu-page,
.ivu-form {
  input {
    &:hover {
      border-color: #f60 !important;
    }
    &:focus {
      border-color: #f60 !important;
      box-shadow: 0 0 0 2px rgba(255, 118, 19, 0.2) !important;
    }
  }
}
/* 博客详情页样式 */
.detail-article {
  .content,
  .v-show-content {
    mark {
      background-color: #fff5f5;
      color: #ff502c;
      padding: 0.065em 0.4em;
      border-radius: 6px;
    }
    p {
      font-size: 14px !important;
    }
    ul li {
      list-style: initial;
    }
    ul,
    ol {
      padding-left: 1em;
      li {
        font-size: 14px !important;
      }
    }
    h1,
    h2,
    h3 {
      position: relative;
      font-weight: 400;
      font-size: 1.34em;
      &::before {
        line-height: 24px;
        font-size: 20px;
        font-weight: bold;
        position: absolute;
        top: 0;
        left: -15px;
        content: '#';
        color: #eb5055;
      }
    }

    h1 {
      font-size: 1.4em;
      &::before {
        font-size: 21px;
        line-height: 25px;
      }
    }

    h3 {
      font-size: 1.18em;
      &::before {
        font-size: 19px;
        line-height: 21px;
      }
    }
    img {
      cursor: zoom-in;
      transition: 0.3s;
    }
  }
}
</style>
