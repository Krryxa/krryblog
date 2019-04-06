<template>
  <main v-if="!isNoBlog">
    <my-header></my-header>
    <Detail :blog="blog"></Detail>
    <my-footer></my-footer>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import Service from '@/service';
export default {
  data () {
    return {
      blog: {},
      status: 200,
    };
  },
  computed: {
    isNoBlog () {
      return this.status === 404;
    },
  },
  created () {
    this.getBlogDetail();
  },
  methods: {
    async getBlogDetail () {
      let id = this.$route.params['id'];
      let res = await Service.getBlogDetail(id);
      this.status = res.status;
      this.blog = res.data;
      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        document.title = `${this.blog['title']} - ${document.title}`;
      }
    },
  },
  watch: {
    $route (to, from) {
      this.getBlogDetail();
    },
  },
  components: {
    Detail: () => import('@/components/Detail'),
  },
};
</script>

<style lang='scss' scoped>

</style>
