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
	 * 查询音乐列表
	 * @return
	 */
	public HashMap<String, Object> getMusic(Params param);
	
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
	
}
