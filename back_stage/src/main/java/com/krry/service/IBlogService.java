package com.krry.service;

import java.util.HashMap;
import java.util.List;

import com.krry.entity.Blog;
import com.krry.entity.Classify;

/**
 * service层：处理业务逻辑（impl里面实现）
 * @author asusaad
 *
 */
public interface IBlogService {
	
	/**
	 * 查询所有博客
	 * @return
	 */
	public HashMap<String, Object> getBlog();
	
	/**
	 * 查询博客的分类
	 * @return
	 */
	public HashMap<String, Object> getClassify();
	
	/**
	 * 增加博客
	 * @return
	 */
	public int addBlog(Blog blog);
	
}
