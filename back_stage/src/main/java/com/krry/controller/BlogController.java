package com.krry.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.krry.entity.Blog;
import com.krry.entity.Params;
import com.krry.service.IBlogService;
import com.krry.util.DeleteFileUtil;
import com.krry.util.UploadUtil;

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
	public HashMap<String, Object> getBlog(Params params){
		
		HashMap<String, Object> resData = blogService.getBlog(params);
		
		return resData;
	}
	
	
	/**
	 * 获取博客详情页
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogDetail/{id}")
	public HashMap<String, Object> getBlogDetail(@PathVariable("id")Integer id){
		
		HashMap<String, Object> resData = blogService.getBlogDetail(id);
		
		return resData;
	}
	
	
	/**
	 * 查询博客的分类
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getClassify")
	public HashMap<String, Object> getClassify(){
		
		HashMap<String, Object> resData = blogService.getClassify();
		
		return resData;
	}
	
	
	/**
	 * 获取分类博客、博客总数
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogByClassifyId/{id}")
	public HashMap<String, Object> getBlogByClassifyId(@PathVariable("id")Integer id, Params params){
		
		HashMap<String, Object> resData = blogService.getBlogByClassifyId(id, params);
		
		return resData;
	}
	
	
	/**
	 * 新增博客
	 * @param blog
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addBlog")
	public int addBlog(Blog blog){
		
		int id = blogService.addBlog(blog);
		
		return id;
	}

	/**
	 * 上传 markdown 内容图片
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/uploadContent/{id}")
	public String uploadContent(@RequestParam("imgFile") MultipartFile file, @PathVariable("id")Integer id, HttpServletRequest request) throws IllegalStateException, IOException {
		
		// 调用工具类完成上传，返回相关数据到页面
		return UploadUtil.markdownUpload(file, request, id);
	}

	/**
	 * 删除文件 controller
	 * 删除 markdown 内容图片
	 * @param filePath
	 */
	@ResponseBody
	@RequestMapping("/deleteFile")
	public String deleteFile(String filePath, HttpServletRequest request) {
		
		return DeleteFileUtil.deleteFile(filePath, request);
	}
	
	
	/**
	 * 上传封面图片
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String krryupload(@RequestParam("imgFile") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		
		// 调用工具类完成上传，返回相关数据到页面
		return UploadUtil.singleupload(file, request);
	}
	
	/**
	 * 修改博客
	 * @param blog
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateBlog")
	public String updateBlog(Blog blog){
		
		String msg = blogService.updateBlog(blog);
		
		return msg;
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
	 * 删除博客封面
	 * @param id
	 * @param filePath
	 */
	@ResponseBody
	@RequestMapping("/deleteBlogCover/{id}")
	public String deleteBlogCover(@PathVariable("id")Integer id, String filePath, HttpServletRequest request) {
		
		String msg = DeleteFileUtil.deleteFile(filePath, request);
		
		// 文件删除成功后，如果不是新增博客（新增博客传入的id 为 0），再调用 sql 删除该条数据的图片地址
		if (msg.equals("success") && id != 0) {
			msg = blogService.deleteBlogCover(id);
		}
		
		return msg;
	}
	
}



