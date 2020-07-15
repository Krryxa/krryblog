<template>
  <div class="header">
    <h1>
      <span class="e-left">『</span>
      <span class="text">{{title}}</span>
      <span class="e-right">』</span>
    </h1>
    <h4>{{description}}</h4>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String
    },
    description: {
      type: String
    }
  },
  data() {
    return {}
  },
  components: {}
}
</script>

<style lang='scss' scoped>
.header {
  padding: 96px 0 28px;
  text-align: center;
  background: url(../assets/pic/category.jpg) no-repeat;
  background-size: cover;

  h1 {
    position: relative;
    margin: 0 auto;
    margin-bottom: 10px;
    font-size: 30px;
    font-weight: 400;
    color: rgba(255, 255, 255, 0.89);
    text-shadow: 0 4px 8px rgba(7, 17, 27, 0.4);
    letter-spacing: 0.1em;

    .e-left {
      position: relative;
      top: -12px;
      left: 2px;
    }

    .e-right {
      position: relative;
      top: -10px;
      right: 2px;
    }

    .text {
      display: inline-block;
      max-width: 660px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

  h4 {
    font-size: 16px;
    font-weight: 400;
    color: rgba(255, 255, 255, 0.54);
  }
}
</style>
