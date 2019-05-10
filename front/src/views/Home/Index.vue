<template>
  <div>
    <my-header></my-header>
    <Content></Content>
    <my-footer></my-footer>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  components: {
    Content: () => import('./Content')
  }
}
</script>

<style lang='scss' scoped>
</style>
