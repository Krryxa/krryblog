<template>
  <main v-if="!isNotCategory">
    <Content :blogList="blogList" :categoryName="categoryName"></Content>
    <Page
      v-if="blogLen > pageSize"
      :total="blogLen"
      size="small"
      :current="pageNo"
      :page-size="pageSize"
      show-elevator
      @on-change="changePage"
    />
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getBlogByClassifyId } from '@/service'
export default {
  data() {
    return {
      blogList: [],
      categoryName: '',
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 9,
      flag: true
    }
  },
  computed: {
    isNotCategory() {
      return this.status === 404
    }
  },
  created() {
    this.getCategory()
  },
  methods: {
    async getCategory() {
      let id = this.$route.params['id']
      // 不在这个范围内的分类 id 是 404 页面
      if (id < 1 || id > 4) {
        this.status = 404
      }
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      let res = await getBlogByClassifyId(id, reqData)
      this.status = res.code

      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        this.blogList = res.result.data
        this.blogLen = res.result.blogLen
        this.categoryName = res.result.categoryName
        document.title = `${this.categoryName} - ${this.documentTitle}`

        // if (this.status === 406) {
        //   // 分类下没有发布过任何博客的情况
        //   // TODO
        //   console.log('分类下没有发布过任何博客的情况');
        // }
      }
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getCategory()
      this.flag = false
      let query = pageNo === 1 ? {} : { page: pageNo }
      this.$router.push({ name: 'category', query: query })
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1
        this.getCategory()
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
