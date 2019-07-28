<template>
  <main v-if="!isEmpty">
    <my-header></my-header>
    <Content :musicList="musicList" @addCount="addCount" @deleteMusic="deleteMusic"></Content>
    <Page
      v-if="musicLen > pageSize"
      :total="musicLen"
      size="small"
      :current="pageNo"
      :page-size="pageSize"
      show-elevator
      @on-change="changePage"
    />
    <my-footer></my-footer>
  </main>
  <not-found v-else></not-found>
</template>

<script>
import { getMusic } from '@/service'
export default {
  data() {
    return {
      musicList: [],
      musicLen: 0,
      status: 200,
      pageNo: +this.$route.query.page || 1,
      pageSize: 10,
      flag: true
    }
  },
  created() {
    this.getMusic()
  },
  computed: {
    isEmpty() {
      return this.status === 404
    }
  },
  methods: {
    async getMusic() {
      let reqData = {
        pageNo: this.pageNo,
        pageSize: this.pageSize
      }
      let res = await getMusic(reqData)
      this.status = res.status
      // 404 的标题在 axios 拦截器已经定义
      if (this.status !== 404) {
        this.musicList = res.data
        this.musicLen = res.musicLen
      }
    },
    addCount() {
      ++this.musicLen
    },
    deleteMusic(id) {
      this.musicList = this.musicList.filter(item => item.id !== id)
      --this.musicLen
      if (this.musicList.length === 0) {
        this.pageNo = --this.pageNo > 0 ? this.pageNo : 1
        this.getMusic()
      }
    },
    async changePage(pageNo) {
      this.pageNo = pageNo
      await this.getMusic()
      this.flag = false
      let query = pageNo === 1 ? {} : { page: pageNo }
      this.$router.push({ name: 'music', query: query })
    }
  },
  watch: {
    $route(to, from) {
      if (this.flag) {
        this.pageNo = +to.query.page || 1
        this.getMusic()
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
