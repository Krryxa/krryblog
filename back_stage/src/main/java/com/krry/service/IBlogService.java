package com.krry.service;

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
	
}
