package com.krry.mapper;

import java.util.List;

import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.Params;

/**
 * 持久层
 * @author w
 *
 */
public interface PartMapper {
	
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
	 * 查询音乐列表
	 * @return
	 */
	public List<Music> getMusic(Params param);
	
	/**
	 * 查询音乐列表总数
	 * @return
	 */
	public int getMusicCount();
	
	
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

}
