import * as Types from './mutations-type.js';
import { getClassify } from '@/service';

export default {
  // 设置博客分类
  async [Types.SETCLASSIFY] ({ commit }) {
    let classifyList = await getClassify();
    commit(Types.SETCLASSIFY, classifyList);
  },
};
