package com.krry.mapper;

import java.util.List;

import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.User;

/**
 * 持久层
 * @author w
 *
 */
public interface AdminMapper {
	
	/**
	 * 根据用户id查询
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	
	/**
	 * 获取博客详情页（编辑）
	 * @return
	 */
	public Blog getBlogDetail(int id);
	
	
	/**
	 * 分页所有博客（发布和未发布）
	 * @return
	 */
	public List<Blog> getBlog();
	
	/**
	 * 博客总数（发布和未发布）
	 * @return
	 */
	public int getBlogCount();
	
	/**
	 * 修改个人信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 上传音乐
	 * @return
	 */
	public void addMusic(Music music);
	
	/**
	 * 删除音乐
	 * @param id
	 */
	public void deleteMusic(int id);
	
	/**
	 * 增加博客
	 * @return
	 */
	public void addBlog(Blog blog);
	
	/**
	 * 修改博客
	 * @param blog
	 */
	public void updateBlog(Blog blog);
	
	/**
	 * 修改博客的其他信息（不修改博客内容）
	 * @param blog
	 */
	public void updateBlogOtherContents(Blog blog);
	
	/**
	 * 删除博客封面
	 * @param id
	 */
	public void deleteBlogCover(int id);

}



