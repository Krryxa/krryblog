package com.krry.service.impl;

import java.text.ParseException;
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
import com.krry.entity.ResponseVal;
import com.krry.entity.User;
import com.krry.mapper.AdminMapper;
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
	private AdminMapper adminMapper;

	@Autowired
	private PartMapper partMapper;
	
	/**
	 * 登录
	 * @return
	 */
	public ResponseVal login(User user){
		
		ResponseVal res = new ResponseVal();
		
		User realUser = partMapper.getUserByName(user);
		res.setCode(200);
		
		if (realUser != null) {
			if (realUser.getPassword().equals(user.getPassword())) {
				res.setMessage("success");
				res.setResult(realUser.getId());
			} else {
				res.setMessage("The password is wrong~");
			}
		} else {
			res.setMessage("The username does not exist~");
		}
		
		return res;
	}
	
	/**
	 * 根据标签查询已发布的博客
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getBlogByTag(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
				
		List<Blog> blogList = partMapper.getBlogByTag(params);
		int blogLen = partMapper.getTagBlogCount(params);
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
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
			res.setCode(200);
		} else {
			// 没有相关标签，不存在
			res.setCode(404);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        List<Blog> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("blogLen", blogLen);
		
		res.setResult(resData);
		
		return res;
	}
	
	
	/**
	 * 搜索已发布的博客
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getBlogBykeyword(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Blog> blogList = partMapper.getBlogBykeyword(params);
		int blogLen = partMapper.getKeyworkBlogCount(params);
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
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
			res.setCode(200);
		} else {
			// 查询无果
			res.setCode(406);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        List<Blog> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("blogLen", blogLen);
		
		res.setResult(resData);
		
		return res;
	}
	
	/**
	 * 查询相关链接 / 关于我
	 * @return
	 */
	public ResponseVal getLinkOrAbout(String title) {
		
		Blog newBlog = new Blog();
		
		Blog blog = partMapper.getLinkOrAbout(title);
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		if (blog != null) {
			// 设置点击量+1
			int id = blog.getId();
			int hit = blog.getHit();
			blog.setHit(++hit);
			newBlog.setHit(hit);
			newBlog.setId(id);
			
			adminMapper.updateBlog(newBlog);
			
			// 处理查询出timestamp时间类型多了个 .0  的问题
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
			try {
				Date createData = df.parse(blog.getCreateTime());
				Date updateData = df.parse(blog.getUpdateTime());
				String createTime = df.format(createData);
				String updateTime = df.format(updateData);
				blog.setCreateTime(createTime);
				blog.setUpdateTime(updateTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			res.setCode(200);
		} else {
			res.setCode(404);
		}
		resData.put("data", blog);
		
		res.setResult(resData);
		
		return res;
	}
	
	
	/**
	 * 查询音乐列表
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getMusic(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Music> musicList = partMapper.getMusic(params);
		int musicLen = partMapper.getMusicCount();
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		int len = musicList.size();
		
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				Music curMusic = musicList.get(i);
				String createTime = curMusic.getCreateTime();
				// 去掉 2018-09-04 13:24:05 的时分秒
				curMusic.setCreateTime(createTime.split(" ")[0]);
			}
			res.setCode(200);
		} else {
			// 查询无果
			res.setCode(406);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Music> pageInfo = new PageInfo<Music>(musicList);
        List<Music> pageBlog = pageInfo.getList();
		resData.put("data", pageBlog);
		resData.put("musicLen", musicLen);
		
		res.setResult(resData);
		
		return res;
	}


}



