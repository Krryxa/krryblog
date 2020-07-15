<template>
  <section ref="blogSection" class="section-article">
    <article v-for="(val, index) in blogShowList" :key="index">
      <span v-if="val.isTop && isHome" class="top-icon">
        <i class="iconfont icon-Up-1" />
      </span>
      <div class="bg-container">
        <div
          class="bg-img"
          :style="val.id | setLink({background: `url(${basePath}${val.image}) 0% 0% / cover`})"
        ></div>
      </div>
      <!-- 这里使用命名路由，效果与下面一样，使用过滤器控制骨架屏的链接 -->
      <router-link :to="val.id | setLink({name: 'blog', params: {id: val.id, title: val.title}})">
        <div class="bg-cover">
          <p v-html="val.description"></p>
        </div>
      </router-link>
      <div class="other-bgcover right-bgcover"></div>
      <div class="other-bgcover"></div>
      <div class="desc">
        <!-- 这里直接用 id 作为路径，使用过滤器控制骨架屏的链接 -->
        <router-link :to="val.id | setLink(`/${val.id}`)">
          <p class="title" :title="val.title">{{val.title}}</p>
        </router-link>
        <div class="desc-bottom">
          <div :class="{'d-detail': true, 'hidden-detail': !val.id}">
            <Icon type="md-calendar" />
            <span>{{val.createTime}}</span>
            <Icon type="md-eye" />
            <span>{{val.hit}}</span>
            <Icon type="md-chatboxes" />
            <span>{{val.comment}}</span>
          </div>
          <router-link :to="val.id | setLink(`/category/${val.classifyId}`)">
            <Tooltip :content="val.classify || 'Krryblog'" theme="light" placement="top-end">
              <div class="item-icon" :style="{backgroundPosition: `0 ${-val.classifyId*70}px`}"></div>
            </Tooltip>
          </router-link>
        </div>
      </div>
    </article>
    <div class="clear"></div>
  </section>
</template>

<script>
export default {
  props: {
    blogList: {
      type: Array
    }
  },
  data() {
    return {
      blogShowList: [
        {
          classify: '',
          title: '',
          description: '',
          id: '',
          image: '',
          createTime: '2018-08-23',
          hit: 20,
          comment: 20,
          classifyId: 1
        },
        {
          classify: '',
          title: '',
          description: '',
          id: '',
          image: '',
          createTime: '2018-08-23',
          hit: 20,
          comment: 20,
          classifyId: 2
        },
        {
          classify: '',
          title: '',
          description: '',
          id: '',
          image: '',
          createTime: '2018-08-23',
          hit: 20,
          comment: 20,
          classifyId: 3
        }
      ]
    }
  },
  filters: {
    setLink(id, link) {
      return id ? link : ''
    }
  },
  created() {
    if (this.blogList.length > 0) {
      this.blogShowList = this.blogList
    }
  },
  computed: {
    basePath() {
      return process.env.API_ROOT + '/krryblog/'
    },
    isHome() {
      return this.$route.name === 'home' || this.$route.name === 'homePage'
    }
  },
  watch: {
    blogList(newVal, oldVal) {
      this.blogShowList = newVal
      if (oldVal.length !== 0) {
        // 共用组件，每次数据变化产生过渡效果
        this.$refs.blogSection.style['display'] = 'none'
        setTimeout(() => {
          this.$refs.blogSection.style['display'] = 'block'
        }, 0)
      }
    }
  },
  methods: {
    // handleIsTopData (data) {
    //   let topData = [];
    //   data = data.filter(item => {
    //     if (item.isTop) {
    //       topData.push(item);
    //       return false;
    //     }
    //     return true;
    //   });
    //   return [...topData, ...data];
    // },
  },
  components: {}
}
</script>

<style lang='scss' scoped>
section {
  box-sizing: border-box;
  max-width: 960px;
  margin: 0 auto;
  animation: fadeIn 0.6s linear;

  article {
    position: relative;
    box-sizing: border-box;
    float: left;
    width: 280px;
    height: 340px;
    margin: 20px;
    overflow: hidden;
    background: rgba(255, 255, 255, 0.5);
    border-radius: 5px;
    box-shadow: 0 2px 9px 0 rgba(0, 0, 0, 0.08);

    &:hover {
      background: #fff;
      box-shadow: 1px 2px 12px 1px rgba(0, 0, 0, 0.15);

      .bg-img {
        filter: blur(3px);
        transform: scale(1.1);
      }

      .bg-cover {
        background-color: rgba(0, 0, 0, 0.5);
        transition: 0.5s;

        p {
          margin-top: 0;
          opacity: 1;
          transition: 0.5s;
        }
      }
    }

    .top-icon {
      position: absolute;
      top: -4px;
      left: -9px;
      width: 46px;
      height: 46px;
      line-height: 44px;
      color: #fff;
      text-align: center;
      background: url('../assets/pic/tag.png');
      background-size: cover;
      opacity: 0.86;

      i {
        font-size: 19px;
      }
    }

    .bg-container {
      &::before {
        position: absolute;
        left: 0;
        z-index: -1;
        width: 100%;
        font-size: 26px;
        line-height: 230px;
        color: #333;
        text-align: center;
        content: 'Loading...';
        background-color: rgba(169, 169, 169, 0.75);
        border-radius: 5px 5px 0 0;
      }

      &::after {
        position: absolute;
        left: 0;
        z-index: -1;
        width: 100%;
        line-height: 230px;
        content: '';
        background-color: rgba(255, 255, 255, 0);
        border-radius: 5px 5px 0 0;
      }

      .bg-img {
        height: 230px;
        border-radius: 5px 5px 0 0;
        transition: all 0.5s ease;
      }
    }

    .bg-cover {
      position: absolute;
      top: 0;
      width: 100%;
      height: 100%;
      padding: 40px 28px;
      overflow: hidden;
      cursor: url(../assets/pic/cursor.cur), pointer !important;

      p {
        display: -webkit-box;
        padding: 0;
        margin: 0;
        margin-top: 26px;
        overflow: hidden;
        font-size: 14px;
        line-height: 26px;
        color: #fff;
        opacity: 0;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 4;
      }
    }

    .other-bgcover {
      position: absolute;
      right: 0;
      bottom: 1px;
      left: 0;
      z-index: 0;
      width: 110%;
      min-height: 100px;
      background-color: #fff;
      transform: rotate(5deg) translate(-10px, -20px);
    }

    .right-bgcover {
      background-color: rgba(0, 0, 0, 0.5) !important;
      opacity: 0.7;
      transform: rotate(-10deg) translate(10px, -30px);
    }

    .desc {
      position: relative;
      box-sizing: border-box;
      width: 100%;
      height: 110px;
      padding: 7px 15px 10px;
      background: #fff;

      .title {
        display: -webkit-inline-box;
        overflow: hidden;
        font-size: 16px;
        color: #000;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;

        &:hover {
          color: #ff8b18;
        }
      }

      .desc-bottom {
        position: absolute;
        right: 0;
        bottom: 0;
        width: 100%;
        padding: 0 15px 10px;
        text-align: right;

        .d-detail {
          position: absolute;
          bottom: 10px;

          i {
            margin-top: -2px;
            font-size: 14px;

            &:not(:first-child) {
              margin-left: 12px;
            }
          }
        }

        .hidden-detail {
          span {
            visibility: hidden;
          }
        }

        .item-icon {
          float: right;
          width: 37px;
          height: 37px;
          background: url(../assets/pic/bg-ico.png) no-repeat;
          background-size: cover;
          border: 1px solid #eaeaea;
          border-radius: 50%;
        }
      }
    }
  }
}

@keyframes fadeInNoOpa {
  from {
    margin-top: -20px;
  }

  to {
    margin-top: 0;
  }
}
</style>
