<template>
  <section>
    <SectionHeader :title="TagName" :description="description"></SectionHeader>
    <SectionArticle :blogList="blogList" class="wrapper"></SectionArticle>
  </section>
</template>

<script>
import SectionHeader from '@/components/SectionHeader';
import SectionArticle from '@/components/SectionArticle';
export default {
  props: {
    blogList: {
      type: Array,
    },
    TagName: {
      type: String,
    },
  },
  data () {
    return {
      description: '相关内容 —— 标签',
    };
  },
  methods: {
  },
  components: {
    SectionHeader, SectionArticle,
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
