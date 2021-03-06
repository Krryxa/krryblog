import * as Types from './mutations-type.js'

export default {
  // 设置用户名
  [Types.SETUSERNAME](state, username) {
    state.username = username
  },
  // 设置用户 ID
  [Types.SETUSERID](state, id) {
    state.id = id
  },
  // 清空用户信息
  [Types.CLEARUSER](state) {
    state.id = ''
    state.username = ''
  }
}
