import Api from './api'

/**
 * 获取博客列表
 * @param {*} reqData
 */
export async function getBlog(reqData) {
  let res = await Api.fetchBlog(reqData)
  return res
}

/**
 * 获取博客详情页
 * @param {*} id
 */
export async function getBlogDetail(id) {
  let res = await Api.fetchBlogDetail(id)
  return res
}

/**
 * 获取博客分类名称
 */
export async function getClassify() {
  let res = await Api.fetchClassify()
  return res
}

/**
 * 根据分类 id 获取博客列表
 * @param {*} id
 * @param {*} reqData
 */
export async function getBlogByClassifyId(id, reqData) {
  let res = await Api.fetchBlogByClassifyId(id, reqData)
  return res
}

/**
 * 添加博客
 * @param {*} reqData
 */
export async function addBlog(reqData) {
  let res = await Api.addBlog(reqData)
  return res
}

/**
 * 修改博客
 * @param {*} reqData
 */
export async function updateBlog(reqData) {
  let res = await Api.updateBlog(reqData)
  return res
}

/**
 * 修改博客，不改变 updateTime
 * @param {*} reqData
 */
export async function updateBlogNoTime(reqData) {
  let res = await Api.updateBlogNoTime(reqData)
  return res
}

/**
 * 增加评论数 +1
 * @param {*} id
 */
export async function addBlogComment(id) {
  let res = await Api.addBlogComment(id)
  return res
}

/**
 * 删除博客封面
 * @param {*} id
 * @param {*} reqData
 */
export async function deleteBlogCover(id, reqData) {
  let res = await Api.deleteBlogCover(id, reqData)
  return res
}

/**
 * 获取《相关链接》或《关于》博客
 * @param {*} reqData
 */
export async function getLinkOrAbout(reqData) {
  let res = await Api.fetchLinkOrAbout(reqData)
  return res
}

/**
 * 获取编辑博客时的详情页（包含 markdown 字段）
 * @param {*} id
 */
export async function getEditBlogDetail(id) {
  let res = await Api.fetchEditBlogDetail(id)
  return res
}

/**
 * 根据博客标签获取博客列表
 * @param {*} reqData
 */
export async function getBlogByTag(reqData) {
  let res = await Api.fetchBlogByTag(reqData)
  return res
}

/**
 * 根据关键词搜索获取博客列表
 * @param {*} reqData
 */
export async function getBlogByKeyword(reqData) {
  let res = await Api.fetchBlogByKeyword(reqData)
  return res
}

/**
 * 登录
 * @param {*} reqData
 */
export async function getLogin(reqData) {
  let res = await Api.fetchLogin(reqData)
  return res
}

/**
 * 注销登录
 * @param {*} reqData
 */
export async function getLogout() {
  let res = await Api.fetchLogout()
  return res
}

/**
 * 查询博客总数
 * @param {*}
 */
export async function getBlogCount() {
  let res = await Api.fetchBlogCount()
  return res
}

/**
 * 修改用户信息
 * @param {*} reqData
 */
export async function updateUser(reqData) {
  let res = await Api.updateUser(reqData)
  return res
}

/**
 * 分页获取所有博客
 * @param {*} reqData
 */
export async function getAllBlogByPage(reqData) {
  let res = await Api.fetchAllBlogByPage(reqData)
  return res
}

/**
 * 获取所有音乐
 * @param {*} reqData
 */
export async function getMusic(reqData) {
  let res = await Api.fetchMuisc(reqData)
  return res
}

/**
 * 删除音乐
 * @param {*} id
 * @param {*} reqData
 */
export async function deleteMusic(id, reqData) {
  let res = await Api.deleteMusic(id, reqData)
  return res
}

/**
 * markdown 内容上传图片
 * @param {*} id
 * @param {*} reqData
 */
export async function uploadContent(id, reqData) {
  let res = await Api.uploadContent(id, reqData)
  return res
}

/**
 * 删除文件
 * @param {*} id
 * @param {*} reqData
 */
export async function deleteFile(reqData) {
  let res = await Api.deleteFile(reqData)
  return res
}

/**
 * 获取所有博客
 *
 */
export async function getAllBlog() {
  let res = await Api.fetchAllBlog()
  return res
}
