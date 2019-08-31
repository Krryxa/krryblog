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
import com.krry.entity.Classify;
import com.krry.entity.Params;
import com.krry.entity.ResponseVal;
import com.krry.mapper.AdminMapper;
import com.krry.mapper.BlogMapper;
import com.krry.service.IBlogService;

/**
 * 实现业务层
 * 实现service层接口，数据处理对接数据库
 * @author krry
 *
 */
@Service
public class BlogService implements IBlogService{
	
	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private BlogMapper blogMapper;
	
	/**
	 * 分页查询博客、博客总数
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getBlog(Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Blog> blogList = blogMapper.getBlog();
		int blogLen = blogMapper.getBlogCount();
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
	 * 获取博客详情页
	 * @return
	 */
	public ResponseVal getBlogDetail(int id){
		
		Blog newBlog = new Blog();
		
		Blog blog = blogMapper.getBlogDetail(id);
		
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		if (blog != null) {
			// 设置点击量+1
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
	 * 查询博客的分类
	 * @param params
	 * @return
	 */
	public ResponseVal getClassify() {
		
		List<Classify> classifyList = blogMapper.getClassify();
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		if (classifyList.size() > 0) {
			res.setCode(200);
		} else {
			// TODO
		}
		resData.put("data", classifyList);
		
		res.setResult(resData);
		
		return res;
	}
	
	/**
	 * 获取分类博客、博客总数
	 * 时间戳截掉时分秒
	 * @param params
	 * @return
	 */
	public ResponseVal getBlogByClassifyId(int id, Params params) {
		
		// 分页
		int pageNo = params.getPageNo();
		int pageSize = params.getPageSize();
		
		PageHelper.startPage(pageNo, pageSize);
		
		List<Blog> blogList = blogMapper.getBlogByClassifyId(id);
		int blogLen = blogMapper.getBlogClassifyCount(id);
		String categoryName = blogMapper.getBlogClassifyName(id);
		
		HashMap<String, Object> resData = new HashMap<>();
		
		ResponseVal res = new ResponseVal();
		
		int len = blogList.size();
		
		if (categoryName != null) {
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
				// 某分类没有发表过博客的情况，或者分页博客数量为空
				res.setCode(404);
			}
		} else {
			// 分类id出错
			res.setCode(404);
		}
		// 用PageInfo对结果进行包装
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        List<Blog> pageBlog = pageInfo.getList();
        
		resData.put("data", pageBlog);
		resData.put("blogLen", blogLen);
		resData.put("categoryName", categoryName);
		
		res.setResult(resData);
		
		return res;
	}


}



