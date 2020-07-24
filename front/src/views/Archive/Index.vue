<template>
  <main>
    <SectionHeader title="时间归档" description="Archive"></SectionHeader>
    <article class="detail-article">
      <div class="year" v-for="ele in Object.entries(dataObj)" :key="ele[0] + 'year'">
        <span class="year-word" @click="handleToogle($event)">{{ ele[0] }}</span>
        <div class="month" style="display: none;">
          <div v-for="jcl in Object.entries(ele[1])" :key="ele[0] + 'year' + jcl[0]">
            <span class="month-word" @click="handleToogle($event)">{{ jcl[0] }}</span>
            <div class="day">
              <div v-for="item in jcl[1]" :key="item.id">
                <span class="day-word">{{ item.day }}日：</span>
                <router-link class="link" :to="'/' + item.id">{{ item.title }}</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </article>
  </main>
</template>

<script>
import { getAllBlog } from '@/service'
import { slideToogle } from '@/util'
export default {
  data() {
    return {
      dataObj: {},
      top: [],
      blogLen: 0
    }
  },
  computed: {},
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      const { result } = await getAllBlog()
      let year = ''
      let month = ''
      let temp = {}
      this.blogLen = result && Array.isArray(result.data) && result.data.length
      result &&
        Array.isArray(result.data) &&
        result.data.forEach((ele) => {
          const timeList = ele.createTime.split('-')
          if (ele.isTop) {
            this.top.push({
              id: ele.id,
              title: ele.title,
              createTime: ele.createTime
            })
          } else {
            if (timeList[0] !== year || timeList[1] !== month) {
              month = timeList[1]
              if (timeList[0] !== year) {
                year = timeList[0]
                this.$set(this.dataObj, year + ' 年', {})
              }
              temp = this.dataObj[year + ' 年']
              // 这里需要加个字符，不能是纯数字，否则 Object.entries 的时候会自动把 10、11 月份提前，不知道为啥
              this.$set(temp, month + '月', [])
            }
            temp[month + '月'].push({
              id: ele.id,
              title: ele.title,
              year,
              month,
              day: timeList[2]
            })
          }
        })
      this.$nextTick(() => {
        const firstDom = document.getElementsByClassName('month')[0]
        slideToogle(firstDom, 600)
      })
    },
    handleToogle(e) {
      const monthDom = e.currentTarget.nextElementSibling
      slideToogle(monthDom, 600)
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
  padding: 0 25px 16px;
  margin: 0 auto;
  margin-top: 26px;
  background-color: #fff;
  animation: fadeIn 0.6s linear;
  font-size: 14px;
  color: #24292e;

  .year {
    .year-word {
      cursor: url(../../assets/pic/cursor.cur), pointer !important;
      margin-bottom: 20px;
      display: inline-block;
      font-size: 1.3rem;
      font-weight: 500;
    }

    .month {
      margin-left: 24px;
      .month-word {
        cursor: url(../../assets/pic/cursor.cur), pointer !important;
        margin-bottom: 12px;
        display: inline-block;
        font-size: 16px;
        font-weight: 500;
      }

      &>div:last-child {
        padding-bottom: 12px;
      }

      .day {
        margin-left: 24px;
        line-height: 30px;
        .day-word {
          font-weight: 500;
          color: #666;
        }

        &>div {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;

          &:last-child {
            margin-bottom: 12px;
          }
        }

        .link {
          color: #24292e;
        }
      }
    }
  }
}
</style>
