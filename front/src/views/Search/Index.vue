<template>
  <main>
    <Header></Header>
    <Content :blogList="blogList" :keyword="keyword" :hasNoResult="hasNoResult"></Content>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage"/>
    <Footer></Footer>
  </main>
</template>

<script>
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import Content from './Content';
import Service from '@/service';
export default {
  data () {
    return {
      blogList: [],
      keyword: '',
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 9,
      flag: true,
    };
  },
  computed: {
    hasNoResult () {
      return this.status === 406;
    },
  },
  created () {
    this.getSearch();
  },
  methods: {
    async getSearch () {
      this.keyword = this.$route.params['keyword'];
      document.title = `${this.keyword} 的搜索结果 - ${document.title}`;
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        keyword: this.keyword,
      };
      let res = await Service.getBlogByKeyword(reqData);
      this.status = res.status;
      this.blogList = res.data;
      this.blogLen = res.blogLen;
    },
    async changePage (pageNo) {
      this.pageNo = pageNo;
      await this.getSearch();
      this.flag = false;
      let query = pageNo === 1 ? {} : { 'page': pageNo };
      this.$router.push({ name: 'search', query: query });
    },
  },
  watch: {
    $route (to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1;
        this.getSearch();
      }
      this.flag = true;
    },
  },
  components: {
    Header, Footer, Content,
  },
};
</script>

<style lang='scss' scoped>

</style>
