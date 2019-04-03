<template>
  <main v-if="!isNoBlog">
    <my-header></my-header>
    <SectionHeader :title="blog.title" :description="description"></SectionHeader>
    <Detail :blog="blog" :isNoBlog="isNoBlog"></Detail>
    <my-footer></my-footer>
  </main>
  <NotFound v-else></NotFound>
</template>

<script>
import Service from '@/service';
export default {
  data () {
    return {
      blog: {},
      status: 200,
      description: 'Network correlation',
    };
  },
  computed: {
    isNoBlog () {
      return this.status === 404;
    },
  },
  created () {
    this.getLink();
  },
  methods: {
    async getLink () {
      let reqData = {
        title: '友情链接',
      };
      // fetch about api
      let res = await Service.getLinkOrAbout(reqData);
      this.status = res.status;
      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        this.blog = res.data;
        document.title = `${this.blog['title']} - ${document.title}`;
      }
    },
  },
  components: {
    SectionHeader: () => import('@/components/SectionHeader'),
    Detail: () => import('@/components/Detail'),
    NotFound: () => import('@/components/NotFound'),
  },
};
</script>

<style lang='scss' scoped>

</style>
