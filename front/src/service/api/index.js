import Ax from '@/service/axios'
import qs from 'qs'

export default {
  // 实际上项目名称就叫 krryblog，而 Tomcat 设置默认首页就是 krryblog，所以 api 接口的访问路径也可以不需要加上 krryblog，默认匹配上 krryblog 这个项目
  // 而 api 接口这里访问路径前面加上 krryblog，是为了 proxyTable 代理的匹配路径统一为 krryblog

  /**
   * 获取博客列表
   * @param {*} reqData
   */
  fetchBlog(reqData) {
    return Ax.get('/krryblog/blog/getBlog', { params: reqData })
  },

  /**
   * 获取博客详情页
   * @param {*} id
   */
  fetchBlogDetail(id) {
    return Ax.get(`/krryblog/blog/getBlogDetail/${id}`)
  },

  /**
   * 获取博客分类名称
   */
  fetchClassify() {
    return Ax.get('/krryblog/blog/getClassify')
  },

  /**
   * 根据分类 id 获取博客列表
   * @param {*} id
   * @param {*} reqData
   */
  fetchBlogByClassifyId(id, reqData) {
    return Ax.get(`/krryblog/blog/getBlogByClassifyId/${id}`, {
      params: reqData
    })
  },

  /**
   * 添加博客
   * @param {*} reqData
   */
  addBlog(reqData) {
    return Ax.post('/krryblog/krry/addBlog', qs.stringify(reqData))
  },

  /**
   * 修改博客
   * @param {*} reqData
   */
  updateBlog(reqData) {
    return Ax.post('/krryblog/krry/updateBlog', qs.stringify(reqData))
  },

  /**
   * 修改博客，不改变 updateTime
   * @param {*} reqData
   */
  updateBlogNoTime(reqData) {
    return Ax.post('/krryblog/krry/updateBlogNoTime', qs.stringify(reqData))
  },

  /**
   * 删除博客封面
   * @param {*} id
   * @param {*} reqData
   */
  deleteBlogCover(id, reqData) {
    return Ax.post(
      `/krryblog/krry/deleteBlogCover/${id}`,
      qs.stringify(reqData)
    )
  },

  /**
   * 获取《相关链接》或《关于》博客
   * @param {*} reqData
   */
  fetchLinkOrAbout(reqData) {
    return Ax.get('/krryblog/part/getLinkOrAbout', { params: reqData })
  },

  /**
   * 获取编辑博客时的详情页（包含 markdown 字段）
   * @param {*} id
   */
  fetchEditBlogDetail(id) {
    return Ax.get(`/krryblog/krry/getBlogDetail/${id}`)
  },

  /**
   * 根据博客标签获取博客列表
   * @param {*} reqData
   */
  fetchBlogByTag(reqData) {
    return Ax.get(`/krryblog/part/getBlogByTag`, { params: reqData })
  },

  /**
   * 根据关键词搜索获取博客列表
   * @param {*} reqData
   */
  fetchBlogByKeyword(reqData) {
    return Ax.get(`/krryblog/part/getBlogBykeyword`, { params: reqData })
  },

  /**
   * 登录
   * @param {*} reqData
   */
  fetchLogin(reqData) {
    return Ax.post(`/krryblog/part/login`, qs.stringify(reqData))
  },

  /**
   * 注销登录
   * @param {*}
   */
  fetchLogout() {
    return Ax.get(`/krryblog/part/logout`)
  },

  /**
   * 获取博客总数
   * @param {*} reqData
   */
  fetchBlogCount() {
    return Ax.get(`/krryblog/krry/getBlogCount`, {})
  },

  /**
   * 修改用户信息
   * @param {*} reqData
   */
  updateUser(reqData) {
    return Ax.post(`/krryblog/krry/updateUser`, qs.stringify(reqData))
  },

  /**
   * 获取所有博客
   * @param {*} reqData
   */
  fetchAllBlog(reqData) {
    return Ax.get(`/krryblog/krry/getBlog`, { params: reqData })
  },

  /**
   * 获取所有音乐
   * @param {*} reqData
   */
  fetchMuisc(reqData) {
    return Ax.get(`/krryblog/part/getMusic`, { params: reqData })
  },

  /**
   * 删除音乐
   * @param {*} id
   * @param {*} reqData
   */
  deleteMusic(id, reqData) {
    return Ax.post(`/krryblog/krry/deleteMusic/${id}`, qs.stringify(reqData))
  },

  /**
   * markdown 内容上传图片
   * @param {*} id
   * @param {*} reqData
   */
  uploadContent(id, reqData) {
    return Ax.post(`/krryblog/krry/uploadContent/${id}`, reqData)
  },

  /**
   * 删除文件
   * @param {*} reqData
   */
  deleteFile(reqData) {
    return Ax.post('/krryblog/krry/deleteFile', qs.stringify(reqData))
  }
}
