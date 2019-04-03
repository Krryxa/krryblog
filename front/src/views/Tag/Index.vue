<template>
  <main v-if="!isNotCategory">
    <my-header></my-header>
    <Content :blogList="blogList" :TagName="TagName"></Content>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage"/>
    <my-footer></my-footer>
  </main>
  <NotFound v-else></NotFound>
</template>

<script>
import NotFound from '@/components/NotFound';
import Content from './Content';
import Service from '@/service';
export default {
  data () {
    return {
      blogList: [],
      TagName: '',
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 9,
      flag: true,
    };
  },
  computed: {
    isNotCategory () {
      return this.status === 404;
    },
  },
  created () {
    this.getTags();
  },
  methods: {
    async getTags () {
      this.TagName = this.$route.params['name'];
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tag: this.TagName,
      };
      let res = await Service.getBlogByTag(reqData);
      this.status = res.status;
      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        this.blogList = res.data;
        this.blogLen = res.blogLen;
        document.title = `${this.TagName} - ${document.title}`;
      }
    },
    async changePage (pageNo) {
      this.pageNo = pageNo;
      await this.getTags();
      this.flag = false;
      let query = pageNo === 1 ? {} : { 'page': pageNo };
      this.$router.push({ name: 'tag', query: query });
    },
  },
  watch: {
    $route (to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1;
        this.getTags();
      }
      this.flag = true;
    },
  },
  components: {
    NotFound, Content,
  },
};
</script>

<style lang='scss' scoped>

</style>
