<template>
  <main v-if="!isEmpty">
    <my-header></my-header>
    <Content :blogList="blogList" @handleChangeBlog="handleChangeBlog" @deleteBlog="deleteBlog"></Content>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage"/>
    <my-footer></my-footer>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getAllBlog } from '@/service';
export default {
  data () {
    return {
      blogList: [],
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 10,
      flag: true,
    };
  },
  created () {
    this.getBlog();
  },
  computed: {
    isEmpty () {
      return this.status === 404;
    },
  },
  methods: {
    async getBlog () {
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      };
      let res = await getAllBlog(reqData);
      this.status = res.status;
      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        // 将点击数和评论数合并
        for (let val of res.data) {
          let hitComment = `${val.hit} / ${val.comment}`;
          delete val.hit;
          delete val.comment;
          val.hitComment = hitComment;
        }
        this.blogList = res.data;
        this.blogLen = res.blogLen;
      }
    },
    handleChangeBlog (reqData) {
      let params = Object.keys(reqData).filter(item => item !== 'id');
      let id = reqData.id;
      let key = params[0];
      for (let val of this.blogList) {
        if (val.id === id) {
          val[key] = reqData[key];
          break;
        }
      }
    },
    deleteBlog (id) {
      this.blogList = this.blogList.filter(item => item.id !== id);
      --this.blogLen;
      if (this.blogList.length === 0) {
        this.pageNo = --this.pageNo > 0 ? this.pageNo : 1;
        this.getBlog();
      }
    },
    async changePage (pageNo) {
      this.pageNo = pageNo;
      await this.getBlog();
      this.flag = false;
      let query = pageNo === 1 ? {} : { 'page': pageNo };
      this.$router.push({ name: 'list', query: query });
    },
  },
  watch: {
    $route (to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1;
        this.getBlog();
      }
      this.flag = true;
    },
  },
  components: {
    Content: () => import('./Content'),
  },
};
</script>

<style lang='scss' scoped>

</style>
