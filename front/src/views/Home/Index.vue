<template>
  <div>
    <Content></Content>
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
