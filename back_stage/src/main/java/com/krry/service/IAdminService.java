package com.krry.service;

import java.util.HashMap;

import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.Params;
import com.krry.entity.User;

/**
 * 业务层
 * @author w
 *
 */
public interface IAdminService {

	/**
	 * 登录
	 * @return
	 */
	public String login(User user);
	
	/**
	 * 获取博客详情页（编辑）
	 * @return
	 */
	public HashMap<String, Object> getBlogDetail(int id);
	
	/**
	 * 查询博客总数（发布和未发布）
	 * @return
	 */
	public int getBlogCount();

	/**
	 * 分页所有博客（发布和未发布）、博客总数
	 * @return
	 */
	public HashMap<String, Object> getBlog(Params parmas);
	
	/**
	 * 修改个人信息
	 * @param user
	 */
	public String updateUser(User user);
	
	/**
	 * 上传音乐（保存数据库）
	 * @return
	 */
	public int addMusic(Music music);
	
	/**
	 * 删除音乐
	 * @param id
	 */
	public String deleteMusic(int id);
	
	/**
	 * 增加博客
	 * @return
	 */
	public int addBlog(Blog blog);
	
	/**
	 * 修改博客
	 * @param blog
	 */
	public String updateBlog(Blog blog);
	
	/**
	 * 修改博客，不改变 updateTime
	 * @param blog
	 */
	public String updateBlogNoTime(Blog blog);
	
	/**
	 * 删除博客封面
	 * @param id
	 */
	public String deleteBlogCover(int id);

}
