<template>
  <main v-if="!isEmpty">
    <Content :blogList="blogList" :TagName="TagName"></Content>
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
import { getBlogByTag } from '@/service'
export default {
  data() {
    return {
      blogList: [],
      TagName: '',
      blogLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 9,
      flag: true
    }
  },
  computed: {
    isEmpty() {
      return this.status === 404
    }
  },
  created() {
    this.getTags()
  },
  methods: {
    async getTags() {
      this.TagName = this.$route.params['name']
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        tag: this.TagName
      }
      let res = await getBlogByTag(reqData)
      this.status = res.code
      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        this.blogList = res.result.data
        this.blogLen = res.result.blogLen
        document.title = `${this.TagName} - ${this.documentTitle}`
      }
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getTags()
      this.flag = false
      let query = pageNo === 1 ? {} : { page: pageNo }
      this.$router.push({ name: 'tag', query: query })
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1
        this.getTags()
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
