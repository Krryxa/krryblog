<template>
  <main>
    <my-header></my-header>
    <Content :blogList="blogList" :keyword="keyword" :hasNoResult="hasNoResult"></Content>
    <Page
      v-if="blogLen > pageSize"
      :total="blogLen"
      size="small"
      :current="pageNo"
      :page-size="pageSize"
      show-elevator
      @on-change="changePage"
    />
    <my-footer></my-footer>
  </main>
</template>

<script>
import { getBlogByKeyword } from '@/service'
export default {
  data() {
    return {
      blogList: [],
      keyword: '',
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 9,
      flag: true
    }
  },
  computed: {
    hasNoResult() {
      return this.status === 406
    }
  },
  created() {
    this.getSearch()
  },
  methods: {
    async getSearch() {
      this.keyword = this.$route.params['keyword']
      document.title = `${this.keyword} 的搜索结果 - ${document.title}`
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        keyword: this.keyword
      }
      let res = await getBlogByKeyword(reqData)
      this.status = res.code
      this.blogList = res.result.data
      this.blogLen = this.hasNoResult ? 0 : res.result.blogLen
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getSearch()
      this.flag = false
      let query = pageNo === 1 ? {} : { page: pageNo }
      this.$router.push({ name: 'search', query: query })
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1
        this.getSearch()
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
