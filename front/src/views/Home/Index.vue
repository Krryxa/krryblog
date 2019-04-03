<template>
  <div>
    <my-header></my-header>
    <Content></Content>
    <my-footer></my-footer>
  </div>
</template>

<script>
import Content from './Content';
export default {
  data () {
    return {
    };
  },
  components: {
    Content,
  },
};
</script>

<style lang='scss' scoped>

</style>
