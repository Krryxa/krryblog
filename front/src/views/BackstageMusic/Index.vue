<template>
  <main v-if="!isEmpty">
    <Content :musicList="musicList" @addCount="addCount" @deleteMusic="deleteMusic"></Content>
    <Page
      v-if="musicLen > pageSize"
      :total="musicLen"
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
      this.status = res.code
      // 404 的标题在 axios 拦截器已经定义
      if (this.status === 200) {
        this.musicList = res.result.data
        this.musicLen = res.result.musicLen
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
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize
      this.changePage(1)
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
