<template>
  <section>
    <SectionHeader :title="keyword" :description="description"></SectionHeader>
    <SectionArticle :blogList="blogList" class="wrapper" v-if="!hasNoResult"></SectionArticle>
    <NoResult v-else></NoResult>
  </section>
</template>

<script>
import SectionHeader from '@/components/SectionHeader';
import SectionArticle from '@/components/SectionArticle';
import NoResult from './NoResult';
export default {
  props: {
    blogList: {
      type: Array,
    },
    keyword: {
      type: String,
    },
    hasNoResult: {
      type: Boolean,
    },
  },
  data () {
    return {
      description: '相关内容 —— 搜索',
    };
  },
  methods: {
  },
  components: {
    SectionHeader, SectionArticle, NoResult,
  },
};
</script>

<style lang='scss' scoped>
section {
  margin: 0 auto;
  .wrapper {
    padding-top: 20px;
  }
}
</style>
