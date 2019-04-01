<template>
  <main v-if="!isNotCategory">
    <Header></Header>
    <Content :blogList="blogList" :categoryName="categoryName" @changePage="changePage"></Content>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage"/>
    <Footer></Footer>
  </main>
  <NotFound v-else></NotFound>
</template>

<script>
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import NotFound from '@/components/NotFound';
import Content from './Content';
import Service from '@/service';
export default {
  data () {
    return {
      blogList: [],
      categoryName: '',
      blogLen: 0,
      status: 200,
      pageNo: 1,
      pageSize: 9,
    };
  },
  computed: {
    isNotCategory () {
      return this.status === 404;
    },
  },
  created () {
    this.getCategory();
  },
  methods: {
    async getCategory () {
      let id = this.$route.params['id'];
      // 不在这个范围内的分类 id 是 404 页面
      if (id < 1 || id > 4) {
        this.status = 404;
      }
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      };
      let res = await Service.getBlogByClassifyId(id, reqData);
      this.status = res.status;

      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        this.blogList = res.data;
        this.blogLen = res.blogLen;
        this.categoryName = res.categoryName;
        document.title = `${this.categoryName} - ${document.title}`;

        if (this.status === 406) {
          // 分类下没有发布过任何博客的情况
          // TODO
          console.log('分类下没有发布过任何博客的情况');
        }
      }
    },
    async changePage (pageNo) {
      this.pageNo = pageNo;
      await this.getCategory();
      this.$router.push({ name: 'category', query: { 'page': pageNo } });
    },
  },
  watch: {
    $route (to, from) {
      // 如果是点击了另一个分类，才触发 getCategory；并重置 pageNo = 1；同一个分类下改变分页 page 参数，不进入这里
      if (to.params.id !== from.params.id) {
        this.pageNo = 1;
        this.getCategory();
      }
    },
  },
  components: {
    Header, Footer, NotFound, Content,
  },
};
</script>

<style lang='scss' scoped>

</style>
