<template>
  <main>
    <SectionArticle :blogList="blogList"></SectionArticle>
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
</template>

<script>
import { getBlog } from '@/service'
export default {
  data() {
    return {
      blogList: [],
      blogLen: 0,
      pageNo: +this.$route.params.pageIndex || 1,
      pageSize: 12,
      flag: true
    }
  },
  created() {
    this.getBlogList()
  },
  methods: {
    async getBlogList() {
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      let res = await getBlog(reqData)
      if (res.code === 200) {
        this.blogList = res.result.data
        this.blogLen = res.result.blogLen
      } else {
        this.$router.push({ name: 'error' })
      }
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getBlogList()
      this.flag = false
      if (pageNo === 1) {
        this.$router.push({ name: 'home' })
      } else {
        this.$router.push({ name: 'homePage', params: { pageIndex: pageNo } })
      }
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.params.pageIndex || 1
        this.getBlogList()
      }
      this.flag = true
    }
  },
  components: {
    SectionArticle: () => import('@/components/SectionArticle')
  }
}
</script>

<style lang='scss' scoped>
main {
  max-width: 1080px;
  margin: 90px auto 0;
}
</style>
