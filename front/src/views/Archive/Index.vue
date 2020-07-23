<template>
  <main>
    <SectionHeader title="时间归档" description="Archive"></SectionHeader>
    <article class="detail-article">
      <div v-for="(ele, index) in dataObj" :key="index">
        {{ ele }}
      </div>
    </article>
  </main>
</template>

<script>
import { getAllBlog } from '@/service'
// import { slideToogle } from '@/util'
export default {
  data() {
    return {
      tableData: [],
      dataObj: {},
      status: 200
    }
  },
  computed: {
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      const { result } = await getAllBlog()
      this.tableData = result.data.map(ele => {
        const timeList = ele.createTime.split('-')
        return {
          id: ele.id,
          title: ele.title,
          year: timeList[0],
          month: timeList[1],
          day: timeList[2]
        }
      })
      let year = this.tableData[0].year
      let month = this.tableData[0].month
      this.$set(this.dataObj, year, {})
      let temp = this.dataObj[year]
      temp[month] = []
      this.tableData.forEach(ele => {
        if (ele.year !== year || ele.month !== month) {
          month = ele.month
          if (ele.year !== year) {
            year = ele.year
            this.$set(this.dataObj, year, {})
          }
          temp = this.dataObj[year]
          temp[month] = []
        }
        temp[month].push(ele)
      })
    }
  },
  components: {
    SectionHeader: () => import('@/components/SectionHeader'),
    Detail: () => import('@/components/Detail')
  }
}
</script>

<style lang='scss' scoped>
article {
  position: relative;
  max-width: 700px;
  padding: 0 25px 30px;
  margin: 0 auto;
  margin-top: 26px;
  background-color: #fff;
  animation: fadeIn 0.6s linear;
}
</style>
