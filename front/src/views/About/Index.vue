<template>
  <main v-if="!isNoBlog">
    <my-header></my-header>
    <SectionHeader :title="blog.title" :description="description"></SectionHeader>
    <Detail :blog="blog" :isNoBlog="isNoBlog"></Detail>
    <my-footer></my-footer>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getLinkOrAbout } from '@/service';
export default {
  data () {
    return {
      blog: {},
      status: 200,
      description: 'About me',
    };
  },
  computed: {
    isNoBlog () {
      return this.status === 404;
    },
  },
  created () {
    this.getAbout();
  },
  methods: {
    async getAbout () {
      let reqData = {
        title: '关于我',
      };
      // fetch about api
      let res = await getLinkOrAbout(reqData);
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
  },
};
</script>

<style lang='scss' scoped>

</style>
