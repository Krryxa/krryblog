package com.krry.mapper;

import java.util.HashMap;
import java.util.List;

import com.krry.entity.Blog;
import com.krry.entity.Classify;

/**
 * 持久层
 * @author w
 *
 */
public interface BlogMapper {
	
	/**
	 * 查询已发布的博客
	 * @return
	 */
	public List<Blog> getBlog();
	
	/**
	 * 查询已发布的博客总数
	 * @return
	 */
	public int getBlogCount();
	
	/**
	 * 获取博客详情页
	 * @return
	 */
	public Blog getBlogDetail(int id);
	
	/**
	 * 获取博客评论数
	 * @return
	 */
	public int getBlogComments(int id);
	
	/**
	 * 查询博客的分类
	 * @return
	 */
	public List<Classify> getClassify();
	
	/**
	 * 查询已发布的分类博客
	 * @return
	 */
	public List<Blog> getBlogByClassifyId(int id);
	
	/**
	 * 查询已发布的分类博客总数
	 * @return
	 */
	public int getBlogClassifyCount(int id);
	
	/**
	 * 查询分类博客的名称
	 * @return
	 */
	public String getBlogClassifyName(int id);

	/**
	 * 查询所有已发布博客
	 * @return
	 */
	public List<Blog> getAllBlog();
	
	/**
	 * 查询汇总数据
	 * @return
	 */
	public HashMap<String, Object> getSummarizedData();
	

}
