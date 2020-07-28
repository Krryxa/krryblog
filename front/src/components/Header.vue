<template>
  <header class="head-page">
    <div class="container">
      <div class="home left">
        <Icon type="md-headset"/>
        <router-link to="/">Krryblog</router-link>
      </div>
      <div
        class="nav-coll-menu"
        ref="collMenuNavFar"
        :class="{'nav-coll-menu-padd': isVisibleNavFar}"
      >
        <span class="icon-menu cross" :class="{'icon-menu-close': isVisibleNavFar}">
          <span class="middle"></span>
        </span>
        <span class="click-fun" @click="closeCollMenu"></span>
        <ul class="nav-far" :class="{'visible-nav-far': isVisibleNavFar}">
          <router-link to="/">
            <li>首页</li>
          </router-link>
          <li class="sub-item">
            存档
            <ul class="nav-child">
              <li v-for="(item, index) in classifyList" :key="index">
                <router-link :to="`/category/${item.id}`">{{item.name}}</router-link>
              </li>
            </ul>
          </li>
          <li class="sub-item">
            时之馆
            <ul class="nav-child">
              <li>
                <router-link to="/archive">时间归档</router-link>
              </li>
              <li>
                <router-link to="/stars">星茶会</router-link>
              </li>
            </ul>
          </li>
          <!-- <li class="sub-item">
            主题
            <ul class="nav-child">
              <li @click="littleThem">
                <router-link to="#">简约优雅</router-link>
              </li>
              <li @click="currentThem">
                <router-link to="#">二次元</router-link>
              </li>
            </ul>
          </li>-->
          <!-- 可使用命名路由 -->
          <router-link :to="{name: 'link'}">
            <li>友链</li>
          </router-link>
          <router-link to="/about">
            <li>关于</li>
          </router-link>
        </ul>
      </div>
      <div class="nav right">
        <ul class="nav-far">
          <router-link to="/">
            <li>
              <Icon type="ios-home"/>首页
            </li>
          </router-link>
          <li class="sub-item">
            <Icon type="md-list-box"/>存档
            <ul class="nav-child">
              <li v-for="(item, index) in classifyList" :key="index">
                <router-link :to="`/category/${item.id}`">{{item.name}}</router-link>
              </li>
            </ul>
          </li>
          <li class="sub-item">
            <Icon type="ios-timer"/>时之馆
            <ul class="nav-child">
              <li>
                <router-link to="/archive">时间归档</router-link>
                <router-link to="/stars">星茶会</router-link>
              </li>
            </ul>
          </li>
          <!-- <li class="sub-item">
            <Icon type="md-color-palette" />
            主题
            <ul class="nav-child">
              <li @click="littleThem">
                <router-link to="#">简约优雅</router-link>
              </li>
              <li @click="currentThem">
                <router-link to="#">二次元</router-link>
              </li>
            </ul>
          </li>-->
          <!-- 可使用命名路由 -->
          <router-link :to="{name: 'link'}">
            <li>
              <Icon type="ios-link" style="font-weight: bold;"/>友链
            </li>
          </router-link>
          <router-link to="/about">
            <li>
              <Icon type="md-heart"/>关于
            </li>
          </router-link>
        </ul>
        <div class="search" @mouseover="focusInp">
          <Icon type="md-search" @click="search"/>
          <input
            type="text"
            class="s-inp"
            ref="searchInp"
            v-model.trim="keyWord"
            @keyup.enter="search"
            placeholder="查找你喜欢的内容..."
            maxlength="30"
            autocomplete="off"
          >
        </div>
      </div>
    </div>
  </header>
</template>

<script>
// import Headroom from 'headroom';
export default {
  data() {
    return {
      keyWord: '',
      isVisibleNavFar: false
    }
  },
  computed: {
    // 从接口查询出分类
    classifyList() {
      return this.$store.getters['blog/classify']
    }
  },
  created() {},
  mounted() {
    // 获取页面元素
    let myElement = document.querySelector('header')
    let headroom = new Headroom(myElement, { // eslint-disable-line
      tolerance: 5,
      offset: 80,
      classes: {
        initial: 'animated',
        pinned: 'slideInDown',
        unpinned: 'slideOutUp'
      }
    })
    headroom.init()
    this.$refs.collMenuNavFar.onmouseleave = () => {
      this.isVisibleNavFar = false
    }
  },
  methods: {
    search() {
      // 不需要去掉字符串中间的空格，两边的空格在 v-model.trim 已经去掉
      // this.keyWord = this.keyWord.replace(/\s*/g, '');
      if (this.keyWord !== '') {
        console.log(this.keyWord)
        console.log('search api...')
        this.$router.push({ name: 'search', params: { keyword: this.keyWord } })
        this.keyWord = ''
      } else {
        this.$refs.searchInp.focus()
      }
    },
    focusInp() {
      setTimeout(() => {
        this.$refs.searchInp.focus()
      }, 100)
    },
    closeCollMenu() {
      this.isVisibleNavFar = !this.isVisibleNavFar
    }
    // littleThem () {
    //   this.$Message.info('已经是当前主题哦~');
    // },
    // currentThem () {
    //   this.$Message.error('该主题暂时未完成~ QAQ');
    // },
  },
  components: {}
}
</script>

<style lang='scss' scoped>
header {
  position: fixed;
  top: 0;
  z-index: 1;
  width: 100%;
  height: 70px;
  background: rgba(255, 255, 255, 0.94);
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);

  .container {
    width: 1080px;
    max-width: 100%;
    height: 100%;
    margin: 0 auto;
    color: #3c3c3c;

    a {
      color: #3c3c3c;
    }

    .home {
      height: 100%;
      font-size: 24px;
      line-height: 70px;

      a {
        position: relative;
        z-index: 1;
        font-size: 20px;
      }
    }

    .nav {
      position: relative;
      height: 100%;
      font-size: 14px;
      line-height: 70px;

      .nav-far {
        margin-right: 38px;

        li {
          position: relative;
          float: left;
          margin: 0 16px;

          &:hover {
            color: #eb5055;
            cursor: url(../assets/pic/cursor.cur), pointer !important;
            transition: 0.4s;
          }

          i {
            margin-top: -3px;
            font-size: 16px;
          }

          &.sub-item {
            &:hover > .nav-child {
              top: 100%;
              visibility: visible;
              opacity: 0.98;
              // transition: all .3s ease-in-out;
            }

            .nav-child {
              position: absolute;
              top: 80%;
              z-index: 1501;
              visibility: hidden;
              background: rgba(255, 255, 255, 0.94);
              box-shadow: 0 0 10px 0 #cacaca;
              opacity: 0;
              transition: all 0.3s ease-in-out;

              li {
                float: none;
                margin: 0;

                a {
                  display: block;
                  width: 156px;
                  height: 50px;
                  padding: 15px 20px;
                  line-height: 20px;
                  transition: 0.4s;

                  &:hover {
                    padding-left: 32px;
                  }
                }
              }
            }
          }
        }
      }

      .search {
        &:hover {
          color: #eb5055;
          transition: 0.4s;

          .s-inp {
            width: 100%;
            visibility: visible;
            opacity: 1;
          }
        }

        i {
          position: absolute;
          top: 20px;
          right: 0;
          z-index: 1;
          width: 30px;
          height: 30px;
          font-size: 18px;
          line-height: 30px;
          text-align: center;
          cursor: url(../assets/pic/cursor.cur), pointer !important;
        }

        .s-inp {
          position: absolute;
          top: 20px;
          right: 0;
          width: 0%;
          height: 30px;
          padding: 0 30px 0 18px;
          font-size: 12px;
          line-height: 30px;
          color: #313131;
          visibility: hidden;
          background-color: #fff;
          border: 1px solid #eb5055;
          border-radius: 20px;
          outline: 0;
          opacity: 0;
          transition: 0.6s;
          -webkit-appearance: none;

          &:focus {
            width: 100%;
            visibility: visible;
            opacity: 1;
          }
        }
      }
    }

    .nav-coll-menu-padd {
      padding-bottom: 29px !important;
    }

    /* 小菜单 */
    .nav-coll-menu {
      position: absolute;
      top: 15px;
      left: 6px;
      z-index: 1;
      box-sizing: content-box;
      display: none;
      width: 20px;
      height: 16px;
      padding: 10px;
      cursor: url(../assets/pic/cursor.cur), pointer;

      .click-fun {
        position: absolute;
        top: 0;
        left: 0;
        width: 40px;
        height: 36px;
      }

      .icon-menu-close {
        transform: rotateZ(360deg);

        &::before {
          top: 7px !important;
          background: #eb5055 !important;
          transform: rotate(45deg) !important;
        }

        &::after {
          bottom: 7px !important;
          background: #eb5055 !important;
          transform: rotate(135deg) !important;
        }

        .middle {
          opacity: 0;
        }
      }

      .icon-menu {
        position: relative;
        display: inline-block;
        width: 20px;
        height: 16px;
        transition: all 0.4s ease-in-out;
        transition-timing-function: cubic-bezier(0.61, 0.04, 0.17, 1.32);

        &::before,
        &::after {
          position: absolute;
          width: 20px;
          height: 2px;
          content: '';
          background: #313131;
          transition: all 0.4s ease-in-out;
          transform-origin: 50% 50% 0;
        }

        &::before {
          top: 0;
        }

        &::after {
          bottom: 0;
        }

        .middle {
          position: absolute;
          top: 50%;
          display: inline-block;
          width: 20px;
          height: 2px;
          margin-top: -1px;
          background: #313131;
          transition: all 0.4s ease-in-out;
        }
      }

      .visible-nav-far {
        margin-top: 24px !important;
        visibility: visible !important;
        opacity: 0.96 !important;
      }

      .nav-far {
        width: 130px;
        margin-top: 0;
        font-size: 14px;
        visibility: hidden;
        background: rgba(255, 255, 255, 0.92);
        box-shadow: 0 0 4px 0 #d4d4d4;
        opacity: 0;
        transition: 0.4s;
        // border-radius: 5px;
        li {
          position: relative;
          padding: 0 22px;
          line-height: 50px;
          transition: 0.4s;

          &:hover {
            padding: 0 32px;
            color: #eb5055;
            cursor: url(../assets/pic/cursor.cur), pointer !important;
            transition: 0.4s;
          }

          i {
            margin-top: -3px;
            font-size: 16px;
          }

          &.sub-item {
            &:hover > .nav-child {
              top: 100%;
              visibility: visible;
              opacity: 0.98;
              // transition: all .3s ease-in-out;
            }

            .nav-child {
              position: absolute;
              top: 38px;
              left: 60px;
              z-index: 1501;
              visibility: hidden;
              background: rgba(255, 255, 255, 0.94);
              box-shadow: 0 0 4px 0 #d4d4d4;
              opacity: 0;
              transition: all 0.3s ease-in-out;

              li {
                float: none;
                padding: 0;
                margin: 0;

                a {
                  display: block;
                  width: 130px;
                  height: 50px;
                  padding: 15px 22px;
                  line-height: 20px;
                  transition: 0.4s;

                  &:hover {
                    padding-left: 32px;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
