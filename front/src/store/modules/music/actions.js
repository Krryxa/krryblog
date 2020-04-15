import * as Types from './mutations-type.js'

export default {
  // 设置当前播放歌曲信息
  async [Types.SETMUSIC]({ commit }, music) {
    commit(Types.SETMUSIC, music)
  }
}
