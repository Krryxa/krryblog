<template>
  <main v-if="!isNoBlog">
    <Detail :blog="blog" @clearBlog="clearBlog"></Detail>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getBlogDetail } from '@/service'
export default {
  data() {
    return {
      blog: {},
      status: 200
    }
  },
  computed: {
    isNoBlog() {
      return this.status === 404
    }
  },
  created() {
    this.getBlog()
  },
  methods: {
    async getBlog() {
      let id = this.$route.params['id']
      let res = await getBlogDetail(id)
      this.status = res.code
      this.blog = res.result.data
      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        document.title = `${this.blog['title']} - ${document.title}`
      }
    },
    clearBlog() {
      this.blog = {}
    }
  },
  watch: {
    $route(to, from) {
      this.getBlog()
    }
  },
  components: {
    Detail: () => import('@/components/Detail')
  }
}
</script>

<style lang='scss' scoped>
</style>
