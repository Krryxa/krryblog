package com.krry.service;

import javax.servlet.http.HttpServletRequest;

import com.krry.entity.Params;
import com.krry.entity.ResponseVal;
import com.krry.entity.User;

/**
 * 业务层
 * @author asusaad
 *
 */
public interface IPartService {
	
	/**
	 * 登录
	 * @return
	 */
	public ResponseVal login(User user, HttpServletRequest request);
	
	/**
	 * 注销登录
	 * @return
	 */
	public String logout(HttpServletRequest request);
	
	/**
	 * 根据标签查询已发布的博客
	 * @return
	 */
	public ResponseVal getBlogByTag(Params param);
	
	/**
	 * 搜索已发布的博客
	 * @return
	 */
	public ResponseVal getBlogBykeyword(Params param);
	
	/**
	 * 查询相关链接 / 关于我
	 * @return
	 */
	public ResponseVal getLinkOrAbout(String title);
	
	
	/**
	 * 查询音乐列表
	 * @return
	 */
	public ResponseVal getMusic(Params param);

	
}
