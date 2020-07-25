package com.krry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krry.entity.Blog;
import com.krry.entity.Params;
import com.krry.entity.ResponseVal;
import com.krry.service.IBlogService;

/**
 * Controller
 * controller层，作为请求转发
 * @author krry
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private IBlogService blogService;
	
	
	/**
	 * 查询博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlog")
	public ResponseVal getBlog(Params params){
		
		ResponseVal resData = blogService.getBlog(params);
		
		return resData;
	}
	
	
	/**
	 * 获取博客详情页
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogDetail/{id}")
	public ResponseVal getBlogDetail(@PathVariable("id")Integer id){
		
		ResponseVal resData = blogService.getBlogDetail(id);
		
		return resData;
	}
	
	
	/**
	 * 查询博客的分类
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClassify")
	public ResponseVal getClassify(){
		
		ResponseVal resData = blogService.getClassify();
		
		return resData;
	}
	
	
	/**
	 * 获取分类博客、博客总数
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogByClassifyId/{id}")
	public ResponseVal getBlogByClassifyId(@PathVariable("id")Integer id, Params params){
		
		ResponseVal resData = blogService.getBlogByClassifyId(id, params);
		
		return resData;
	}
	
	/**
	 * 修改博客，不改变 updateTime
	 * @param blog
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateBlogNoTime")
	public String updateBlogNoTime(Blog blog){
		
		String msg = blogService.updateBlogNoTime(blog);
		
		return msg;
	}
	
	/**
	 * 增加评论数 +1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addBlogComment/{id}")
	public int addBlogComment(@PathVariable("id")Integer id){
		
		int comments = blogService.addBlogComment(id);
		
		return comments;
	}


	/**
	 * 查询所有已发布博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllBlog")
	public ResponseVal getAllBlog(@RequestParam("type") String type){
		
		ResponseVal resData = blogService.getAllBlog(type);
		
		return resData;
	}
	
	/**
	 * 查询更新历程
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReviseList")
	public ResponseVal getReviseList(){
		
		ResponseVal resData = blogService.getReviseList();
		
		return resData;
	}
	
	/**
	 * 查询汇总数据
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSummarizedData")
	public ResponseVal getSummarizedData(){
		
		ResponseVal resData = blogService.getSummarizedData();
		
		return resData;
	}
	
}



