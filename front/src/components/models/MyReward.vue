<template>
  <div class="pay">
    <div class="p-box">
      <i class="alipay iconfont icon-alipay-fill" />
      <img class="ali-img" width="130" src="../../assets/pic/alipay.jpg" />
    </div>
    <div class="p-box">
      <i class="wechat iconfont icon-wechat" />
      <img class="we-img" width="130" src="../../assets/pic/wechat.jpg" />
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {}
  },
  created() {},
  mounted() {},
  methods: {}
}
</script>

<style lang='scss' scoped>
.pay {
  user-select: none;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  margin: 20px 0;
  text-align: center;
  transition: 0.3s;
  opacity: 0.3;

  &:hover {
    opacity: 1;
  }

  .p-box {
    margin: 0 20px;
    display: inline;
    position: relative;
    i {
      font-size: 34px;
      cursor: url(../../assets/pic/cursor.cur), pointer;

      &.alipay {
        color: #019fe8;
      }
      &.wechat {
        color: #1caa38;
      }

      &:hover +img {
        opacity: 1;
        visibility: visible;
      }
    }
    img {
      position: absolute;
      bottom: 34px;
      visibility: hidden;
      opacity: 0;
      transition: 0.3s;
      &.ali-img {
        left: -9px;
      }
      &.we-img {
        right: -9.5px;
      }
    }
  }
}
</style>
