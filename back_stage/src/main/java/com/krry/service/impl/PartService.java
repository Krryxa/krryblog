package com.krry.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.Params;
import com.krry.mapper.BlogMapper;
import com.krry.mapper.PartMapper;
import com.krry.service.IPartService;

/**
 * 实现业务层
 * 实现service层接口，数据处理对接数据库
 * @author krry
 *
 */
@Service
public class PartService implements IPartService{

	@Autowired
	private PartMapper partMapper;
	
	/**
	 * 根据标签查询已发布的博客
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> getBlogByTag(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
				
		List<Blog> blogList = partMapper.getBlogByTag(params);
		int blogLen = partMapper.getTagBlogCount(params);
		HashMap<String, Object> resData = new HashMap<>();
		
		int len = blogList.size();
		
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				Blog curBlog = blogList.get(i);
				String createTime = curBlog.getCreateTime();
				String updateTime = curBlog.getUpdateTime();
				// 去掉 2018-09-04 13:24:05 的时分秒
				curBlog.setCreateTime(createTime.split(" ")[0]);
				curBlog.setUpdateTime(updateTime.split(" ")[0]);
			}
			resData.put("status", 200);
		} else {
			// 没有相关标签，不存在
			resData.put("status", 404);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        List<Blog> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("blogLen", blogLen);
		
		return resData;
	}
	
	
	/**
	 * 搜索已发布的博客
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> getBlogBykeyword(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Blog> blogList = partMapper.getBlogBykeyword(params);
		int blogLen = partMapper.getKeyworkBlogCount(params);
		HashMap<String, Object> resData = new HashMap<>();
		
		int len = blogList.size();
		
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				Blog curBlog = blogList.get(i);
				String createTime = curBlog.getCreateTime();
				String updateTime = curBlog.getUpdateTime();
				// 去掉 2018-09-04 13:24:05 的时分秒
				curBlog.setCreateTime(createTime.split(" ")[0]);
				curBlog.setUpdateTime(updateTime.split(" ")[0]);
			}
			resData.put("status", 200);
		} else {
			// 查询无果
			resData.put("status", 406);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        List<Blog> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("blogLen", blogLen);
		
		return resData;
	}
	
	
	/**
	 * 查询音乐列表
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> getMusic(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Music> musicList = partMapper.getMusic(params);
		int musicLen = partMapper.getMusicCount();
		HashMap<String, Object> resData = new HashMap<>();
		
		int len = musicList.size();
		
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				Music curMusic = musicList.get(i);
				String createTime = curMusic.getCreateTime();
				// 去掉 2018-09-04 13:24:05 的时分秒
				curMusic.setCreateTime(createTime.split(" ")[0]);
			}
			resData.put("status", 200);
		} else {
			// 查询无果
			resData.put("status", 406);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Music> pageInfo = new PageInfo<Music>(musicList);
        List<Music> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("musicLen", musicLen);
		
		return resData;
	}
	
	/**
	 * 上传音乐（保存数据库）
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public int addMusic(Music music) {

		partMapper.addMusic(music);
		int id = music.getId();
		
		return id;
		
	}
	
	/**
	 * 删除音乐
	 * @param id
	 */
	public String deleteMusic(int id) {
		
		partMapper.deleteMusic(id);
		
		return "success";
	}


}



