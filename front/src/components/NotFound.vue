<template>
  <main>
    <section>
      <p class="number">404</p>
      <p>很抱歉，找不到页面~~</p>
      <router-link to="/">
        <Button type="error">返回首页</Button>
      </router-link>
    </section>
  </main>
</template>

<script>
export default {
  data() {
    return {}
  },
  components: {}
}
</script>

<style lang='scss' scoped>
section {
  padding: 170px 0;
  margin: 0 auto;
  text-align: center;
  animation: fadeIn 0.6s linear;

  .number {
    font-size: 98px;
    font-weight: 700;
    line-height: 150px;
    color: #252932;
    text-shadow:
      rgba(61, 61, 61, 0.3) 1px 1px,
      rgba(61, 61, 61, 0.2) 2px 2px,
      rgba(61, 61, 61, 0.3) 3px 3px;
  }

  p {
    font-size: 30px;
  }

  button {
    margin-top: 32px;
    font-size: 14px;
  }
}
</style>
