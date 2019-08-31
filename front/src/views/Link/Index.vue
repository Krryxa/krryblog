<template>
  <main v-if="!isNoBlog">
    <my-header></my-header>
    <SectionHeader :title="blog.title" :description="description"></SectionHeader>
    <Detail :blog="blog" :isNoBlog="isNoBlog"></Detail>
    <my-footer></my-footer>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getLinkOrAbout } from '@/service'
export default {
  data() {
    return {
      blog: {},
      status: 200,
      description: 'Website link'
    }
  },
  computed: {
    isNoBlog() {
      return this.status === 404
    }
  },
  created() {
    this.getLink()
  },
  methods: {
    async getLink() {
      let reqData = {
        title: '友情链接'
      }
      // fetch about api
      let res = await getLinkOrAbout(reqData)
      this.status = res.code
      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        this.blog = res.result.data
        document.title = `${this.blog['title']} - ${document.title}`
      }
    }
  },
  components: {
    SectionHeader: () => import('@/components/SectionHeader'),
    Detail: () => import('@/components/Detail')
  }
}
</script>

<style lang='scss' scoped>
</style>
