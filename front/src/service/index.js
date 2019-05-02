import Api from './api';

/**
 * 获取博客列表
 * @param {*} reqData
 */
export async function getBlog (reqData) {
  let res = await Api.fetchBlog(reqData);
  return res;
};

/**
 * 获取博客详情页
 * @param {*} id
 */
export async function getBlogDetail (id) {
  let res = await Api.fetchBlogDetail(id);
  return res;
};

/**
 * 获取博客分类名称
 */
export async function getClassify () {
  let res = await Api.fetchClassify();
  return res.data;
};

/**
 * 根据分类 id 获取博客列表
 * @param {*} id
 * @param {*} reqData
 */
export async function getBlogByClassifyId (id, reqData) {
  let res = await Api.fetchBlogByClassifyId(id, reqData);
  return res;
};

/**
 * 添加博客
 * @param {*} reqData
 */
export async function addBlog (reqData) {
  let res = await Api.addBlog(reqData);
  return res;
};

/**
 * 修改博客
 * @param {*} reqData
 */
export async function updateBlog (reqData) {
  let res = await Api.updateBlog(reqData);
  return res;
};

/**
 * 修改博客，不改变 updateTime
 * @param {*} reqData
 */
export async function updateBlogNoTime (reqData) {
  let res = await Api.updateBlogNoTime(reqData);
  return res;
};

/**
 * 删除博客封面
 * @param {*} id
 * @param {*} reqData
 */
export async function deleteBlogCover (id, reqData) {
  let res = await Api.deleteBlogCover(id, reqData);
  return res;
};

/**
 * 获取《相关链接》或《关于》博客
 * @param {*} reqData
 */
export async function getLinkOrAbout (reqData) {
  let res = await Api.fetchLinkOrAbout(reqData);
  return res;
};

/**
 * 获取编辑博客时的详情页（包含 markdown 字段）
 * @param {*} id
 */
export async function getEditBlogDetail (id) {
  let res = await Api.fetchEditBlogDetail(id);
  return res;
};

/**
 * 根据博客标签获取博客列表
 * @param {*} reqData
 */
export async function getBlogByTag (reqData) {
  let res = await Api.fetchBlogByTag(reqData);
  return res;
};

/**
 * 根据关键词搜索获取博客列表
 * @param {*} reqData
 */
export async function getBlogByKeyword (reqData) {
  let res = await Api.fetchBlogByKeyword(reqData);
  return res;
};

/**
 * 登录
 * @param {*} reqData
 */
export async function getLogin (reqData) {
  let res = await Api.fetchLogin(reqData);
  return res;
};

/**
 * 修改用户信息
 * @param {*} reqData
 */
export async function updateUser (reqData) {
  let res = await Api.updateUser(reqData);
  return res;
};

/**
 * 获取所有博客
 * @param {*} reqData
 */
export async function getAllBlog (reqData) {
  let res = await Api.fetchAllBlog(reqData);
  return res;
};
