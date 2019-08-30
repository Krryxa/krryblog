package com.krry.service;

import java.util.HashMap;

import com.krry.entity.Music;
import com.krry.entity.Params;

/**
 * 业务层
 * @author asusaad
 *
 */
public interface IPartService {
	
	/**
	 * 根据标签查询已发布的博客
	 * @return
	 */
	public HashMap<String, Object> getBlogByTag(Params param);
	
	/**
	 * 搜索已发布的博客
	 * @return
	 */
	public HashMap<String, Object> getBlogBykeyword(Params param);
	
	/**
	 * 查询相关链接 / 关于我
	 * @return
	 */
	public HashMap<String, Object> getLinkOrAbout(String title);
	
	
	/**
	 * 查询音乐列表
	 * @return
	 */
	public HashMap<String, Object> getMusic(Params param);

	
}
