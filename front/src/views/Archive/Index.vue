<template>
  <main>
    <SectionHeader :title="title" :description="description"></SectionHeader>
    <article class="detail-article" ref="container">
      <div class="summarize" v-if="!isRevise">
        <div class="text">
          <span>数据统计</span>
        </div>
        <ul>
          <li>
            <span class="num">{{ blogLen }}</span>
            <span>文章</span>
          </li>
          <li>
            <span class="num">4</span>
            <span>类别</span>
          </li>
          <li>
            <span class="num">{{ formatKM(summarizedData.commentSum, 1) }}+</span>
            <span>评论</span>
          </li>
          <li>
            <span class="num">{{ formatKM(summarizedData.hitSum, 1) }}+</span>
            <span>阅读</span>
          </li>
          <div class="clear"></div>
        </ul>
      </div>
      <div class="year" v-for="ele in Object.entries(dataObj)" :key="ele[0] + 'year'">
        <span class="year-word" @click="handleToogle($event)">{{ ele[0] }}</span>
        <div class="month" style="display: none;">
          <div v-for="jcl in Object.entries(ele[1])" :key="ele[0] + 'year' + jcl[0]">
            <span class="month-word" @click="handleToogle($event)">{{ jcl[0] }}</span>
            <div class="day">
              <div v-for="item in jcl[1]" :key="item.id">
                <span class="day-word">{{ item.day }}日{{item.remark}}：</span>
                <router-link v-if="!isRevise" class="link" :to="'/' + item.id">{{ item.title }}</router-link>
                <span v-else class="link">{{ item.title }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </article>
  </main>
</template>

<script>
import { getAllBlog, getReviseList, getSummarizedData } from '@/service'
import { formatKM, slideToogle } from '@/util'
export default {
  data() {
    return {
      summarizedData: {},
      dataObj: {},
      blogLen: 0,
      formatKM: formatKM
    }
  },
  computed: {
    isRevise() {
      return this.$route.name === 'revise'
    },
    title() {
      return this.isRevise ? '更新历程' : '时间归档'
    },
    description() {
      return this.isRevise ? 'Experience' : 'Archive'
    }
  },
  watch: {
    $route(to, from) {
      this.$refs.container.style.display = 'none'
      setTimeout(() => {
        this.$refs.container.style.display = 'block'
      }, 0)
      this.blogLen = '--'
      this.getBlogData()
      !this.isRevise && this.summarize()
    }
  },
  created() {
    this.getBlogData()
    this.summarize()
  },
  methods: {
    async summarize() {
      const { result } = await getSummarizedData()
      this.summarizedData = result ? result.data : {}
    },
    async getBlogData() {
      this.$set(this, 'dataObj', {})
      const { result } = this.isRevise ? await getReviseList() : await getAllBlog({
        type: 'NO'
      })
      let year = ''
      let month = ''
      let temp = {}
      this.blogLen = result && Array.isArray(result.data) && result.data.length
      result &&
        Array.isArray(result.data) &&
        result.data.forEach((ele) => {
          const timeList = ele.createTime.split('-')
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
            day: timeList[2],
            remark: ele.remark
          })
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

  .summarize {
    border: 1px dashed #e0e0e0;
    padding: 20px 50px;
    margin: 32px 0 24px;
    border-radius: 6px;

    .text {
      color: #fff;
      padding: 4.5px;
      position: absolute;
      top: -15px;
      left: 56px;
      background: #fff;

      span {
        background: #eb5055;
        padding: 6px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 500;
      }
    }

    ul li {
      float: left;
      width: 25%;
      text-align: center;
      padding: 6px 0;
      color: #666;
      font-weight: 500;

      .num {
        width: 100%;
        display: inline-block;
        font-size: 32px;
        color: #adabab;
      }
    }
  }

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
