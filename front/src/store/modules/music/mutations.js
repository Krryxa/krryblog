import * as Types from './mutations-type.js'

export default {
  // 设置当前播放歌曲信息
  [Types.SETMUSIC](state, music) {
    state.music = music
  }
}
