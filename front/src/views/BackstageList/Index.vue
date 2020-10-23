<template>
  <main v-if="!isEmpty">
    <Content :blogList="blogList" @handleChangeBlog="handleChangeBlog" @deleteBlog="deleteBlog"></Content>
    <Page
      v-if="blogLen > pageSize"
      :total="blogLen"
      size="small"
      :current="pageNo"
      :page-size="pageSize"
      show-elevator
      show-sizer
      :page-size-opts="[10, 20, 50]"
      @on-change="changePage"
      @on-page-size-change="changePageSize"
    />
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getAllBlogByPage } from '@/service'
export default {
  data() {
    return {
      blogList: [],
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 10,
      flag: true
    }
  },
  created() {
    this.getBlog()
  },
  computed: {
    isEmpty() {
      return this.status === 404
    }
  },
  methods: {
    async getBlog() {
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      let res = await getAllBlogByPage(reqData)
      this.status = res.code
      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        // 将点击数和评论数合并
        for (let val of res.result.data) {
          let hitComment = `${val.hit} / ${val.comment}`
          delete val.hit
          delete val.comment
          val.hitComment = hitComment
        }
        this.blogList = res.result.data
        this.blogLen = res.result.blogLen
      }
    },
    handleChangeBlog(reqData) {
      let params = Object.keys(reqData).filter(item => item !== 'id')
      let id = reqData.id
      let key = params[0]
      for (let val of this.blogList) {
        if (val.id === id) {
          val[key] = reqData[key]
          break
        }
      }
    },
    deleteBlog(id) {
      this.blogList = this.blogList.filter(item => item.id !== id)
      --this.blogLen
      if (this.blogList.length === 0) {
        this.pageNo = --this.pageNo > 0 ? this.pageNo : 1
        this.getBlog()
      }
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getBlog()
      this.flag = false
      let query = pageNo === 1 ? {} : { page: pageNo }
      this.$router.push({ name: 'list', query: query })
    },
    async changePageSize(pageSize) {
      this.pageSize = pageSize
      await this.getBlog()
      this.flag = false
      let query = pageSize === 10 ? {} : { pageSize: pageSize }
      this.$router.push({ name: 'list', query: query })
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1
        this.pageSize = +to.query.pageSize || 10
        this.getBlog()
      }
      this.flag = true
    }
  },
  components: {
    Content: () => import('./Content')
  }
}
</script>

<style lang='scss' scoped>
</style>
