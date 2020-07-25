package com.krry.service;

import com.krry.entity.Blog;
import com.krry.entity.Params;
import com.krry.entity.ResponseVal;

/**
 * 业务层
 * @author asusaad
 *
 */
public interface IBlogService {
	
	/**
	 * 分页查询博客、博客总数
	 * @return
	 */
	public ResponseVal getBlog(Params params);
	
	/**
	 * 获取博客详情页
	 * @return
	 */
	public ResponseVal getBlogDetail(int id);
	
	/**
	 * 查询博客的分类
	 * @return
	 */
	public ResponseVal getClassify();
	
	/**
	 * 获取分类博客、博客总数
	 * @return
	 */
	public ResponseVal getBlogByClassifyId(int id, Params params);
	
	/**
	 * 修改博客，不改变 updateTime（增加评论数）
	 * @param blog
	 */
	public String updateBlogNoTime(Blog blog);
	
	/**
	 * 增加评论数 +1
	 * @param id
	 */
	public int addBlogComment(Integer id);

	/**
	 * 查询所有已发布博客
	 * @return
	 */
	public ResponseVal getAllBlog(String type);
	
	/**
	 * 查询更新历程
	 * @return
	 */
	public ResponseVal getReviseList();
	
	/**
	 * 查询汇总数据
	 * @return
	 */
	public ResponseVal getSummarizedData();
	
}
