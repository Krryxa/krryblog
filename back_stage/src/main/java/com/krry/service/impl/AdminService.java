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
import com.krry.mapper.BlogMapper;
import com.krry.service.IAdminService;

/**
 * 实现业务层
 * 实现service层接口，数据处理对接数据库
 * @author krry
 *
 */
@Service
public class AdminService implements IAdminService{

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private BlogMapper blogMapper;

	
	/**
	 * 获取博客详情页（编辑）
	 * @return
	 */
	public ResponseVal getBlogDetail(int id){
		
		Blog blog = adminMapper.getBlogDetail(id);
		
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		if (blog != null) {
			
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
	 * 查询博客总数（发布和未发布）
	 * @param
	 * @return
	 */
	public int getBlogCount() {
		int blogLen = adminMapper.getBlogCount();
		return blogLen;
	}

	/**
	 * 分页所有博客（发布和未发布）、博客总数
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getBlog(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Blog> blogList = adminMapper.getBlog();
		int blogLen = adminMapper.getBlogCount();
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
	 * 修改个人信息
	 * @param user
	 * @return
	 */
	public String updateUser(User user) {
		
		if (user.getOriginWord() != null) {
			// 如果要修改密码
			int id = user.getId();
			User originUser = adminMapper.getUserById(id);
			// 查询原密码是否输入正确
			if (!originUser.getPassword().equals(user.getOriginWord())) {
				return "Incorrect input of original password";
			}
		}
		
		adminMapper.updateUser(user);
		
		return "success";
	}
	
	
	/**
	 * 上传音乐（保存数据库）
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public int addMusic(Music music) {

		adminMapper.addMusic(music);
		int id = music.getId();
		
		return id;
		
	}
	
	/**
	 * 删除音乐
	 * @param id
	 */
	public String deleteMusic(int id) {
		
		adminMapper.deleteMusic(id);
		
		return "success";
	}
	
	
	/**
	 * 新增博客
	 * @param blog
	 * @return
	 */
	public int addBlog(Blog blog) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
		String date = df.format(new Date()); // new Date()为获取当前系统时间，也可使用当前时间戳
		blog.setHit(0);
		blog.setComment(0);
		blog.setCreateTime(date);
		blog.setUpdateTime(date);
		blog.setIsDelete(0);
		blog.setIsTop(0);
		
		adminMapper.addBlog(blog);
		int id = blog.getId();
		
		return id;
	}

	
	/**
	 * 修改博客
	 * @param blog
	 * @return
	 */
	public String updateBlog(Blog blog) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
		String date = df.format(new Date()); // new Date()为获取当前系统时间，也可使用当前时间戳
		blog.setUpdateTime(date);
		
		adminMapper.updateBlog(blog);
		
		return "success";
	}
	
	/**
	 * 修改博客，不改变 updateTime
	 * @param blog
	 * @return
	 */
	public String updateBlogNoTime(Blog blog) {
		
		adminMapper.updateBlog(blog);
		
		return "success";
	}
	
	
	/**
	 * 删除博客封面
	 * @param id
	 */
	public String deleteBlogCover(int id) {
		
		adminMapper.deleteBlogCover(id);
		
		return "success";
	}


}



