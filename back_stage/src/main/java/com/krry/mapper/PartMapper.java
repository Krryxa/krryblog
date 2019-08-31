package com.krry.mapper;

import java.util.List;

import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.Params;
import com.krry.entity.User;

/**
 * 持久层
 * @author w
 *
 */
public interface PartMapper {
	
	/**
	 * 根据用户名查询
	 * @return
	 */
	public User getUserByName(User user);
	
	/**
	 * 根据标签查询已发布的博客
	 * @return
	 */
	public List<Blog> getBlogByTag(Params param);
	
	
	/**
	 * 根据标签查询已发布的博客的总数
	 * @return
	 */
	public int getTagBlogCount(Params param);
	
	/**
	 * 搜索已发布的博客
	 * @return
	 */
	public List<Blog> getBlogBykeyword(Params param);
	
	
	/**
	 * 搜索已发布的博客的总数
	 * @return
	 */
	public int getKeyworkBlogCount(Params param);
	
	/**
	 * 查询相关链接 / 关于我
	 * @return
	 */
	public Blog getLinkOrAbout(String title);
	
	/**
	 * 查询音乐列表
	 * @return
	 */
	public List<Music> getMusic(Params param);
	
	/**
	 * 查询音乐列表总数
	 * @return
	 */
	public int getMusicCount();
	

}
