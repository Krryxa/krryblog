<template>
  <main>
    <my-header></my-header>
    <Content :blogList="blogList" @statusBlog="statusBlog" @deleteBlog="deleteBlog"></Content>
    <Page v-if="blogLen > pageSize" :total="blogLen" size="small" :current="pageNo" :page-size="pageSize" show-elevator @on-change="changePage"/>
    <my-footer></my-footer>
  </main>
</template>

<script>
import Content from './Content';
import Service from '@/service';
export default {
  data () {
    return {
      blogList: [],
      blogLen: 0,
      pageNo: +this.$route.query.page || 1,
      pageSize: 10,
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
      let res = await Service.getAllBlog(reqData);
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
    statusBlog (reqData) {
      let id = reqData.id;
      let status = reqData.status;
      for (let val of this.blogList) {
        if (val.id === id) {
          val.status = status;
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
    Content,
  },
};
</script>

<style lang='scss' scoped>

</style>
