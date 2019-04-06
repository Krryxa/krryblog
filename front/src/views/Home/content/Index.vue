<template>
  <main>
    <SectionArticle :blogList="blogList"></SectionArticle>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage" />
  </main>
</template>

<script>
import Service from '@/service';
export default {
  data () {
    return {
      blogList: [],
      blogLen: 0,
      pageNo: +this.$route.params.pageIndex || 1,
      pageSize: 12,
      flag: true,
    };
  },
  created () {
    this.getBlog();
  },
  methods: {
    async getBlog () {
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      };
      let res = await Service.getBlog(reqData);
      if (res.status !== 404) {
        this.blogList = res.data;
        this.blogLen = res.blogLen;
      } else {
        this.$router.push({ name: 'error' });
      }
    },
    async changePage (pageNo) {
      this.pageNo = pageNo;
      await this.getBlog();
      this.flag = false;
      if (pageNo === 1) {
        this.$router.push({ name: 'home' });
      } else {
        this.$router.push({ name: 'homePage', params: { 'pageIndex': pageNo } });
      }
    },
  },
  watch: {
    $route (to, from) {
      if (this.flag) {
        this.pageNo = +to.params.pageIndex || 1;
        this.getBlog();
      }
      this.flag = true;
    },
  },
  components: {
    SectionArticle: () => import('@/components/SectionArticle'),
  },
};
</script>

<style lang='scss' scoped>
main {
  max-width: 1080px;
  margin: 90px auto 0;
}
</style>
