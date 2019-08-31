package com.krry.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.json.JSONUtils;
import com.krry.entity.Blog;
import com.krry.entity.Music;
import com.krry.entity.Params;
import com.krry.entity.ResponseVal;
import com.krry.entity.User;
import com.krry.service.IAdminService;
import com.krry.util.DeleteFileUtil;
import com.krry.util.UploadUtil;

/**
 * Controller
 * controller层，作为请求转发
 * @author krry
 *
 */
@Controller
@RequestMapping("/krry")
public class AdminController {
	
	@Autowired
	private IAdminService adminService;
	
	
	/**
	 * 获取博客详情页（编辑）
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogDetail/{id}")
	public ResponseVal getBlogDetail(@PathVariable("id")Integer id){
		
		ResponseVal resData = adminService.getBlogDetail(id);
		
		return resData;
	}

	/**
	 * 查询博客总数（发布和未发布）
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogCount")
	public int getBlogCount(){
		
		int count = adminService.getBlogCount();
		
		return count;
	}
	
	
	/**
	 * 分页所有博客（发布和未发布）、博客总数
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlog")
	public ResponseVal getBlog(Params params){
		
		ResponseVal resData = adminService.getBlog(params);
		
		return resData;
	}
	
	/**
	 * 修改个人信息
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUser")
	public String updateUser(User user){
		
		String msg = adminService.updateUser(user);
		
		return msg;
	}
	
	
	/**
	 * 上传音乐
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/uploadMusic")
	public String uploadMusic(@RequestParam("musicFile") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		
		// 调用工具类完成上传
		HashMap<String, Object> map = UploadUtil.uploadMusic(file, request);
		
		Music music = new Music();
		music.setTitle((String) map.get("title"));
		music.setSize((String) map.get("size"));
		
		// 保存数据库
		int id = adminService.addMusic(music);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // 设置日期格式
		String date = df.format(new Date()); // new Date()为获取当前系统时间，也可使用当前时间戳
		
		map.put("id", id);
		map.put("createTime", date);
		
		return JSONUtils.toJSONString(map);
	}
	
	/**
	 * 删除音乐
	 * @param id
	 * @param filePath
	 */
	@ResponseBody
	@RequestMapping("/deleteMusic/{id}")
	public String deleteMusic(@PathVariable("id")Integer id, String filePath, HttpServletRequest request) {
		
		String msg = DeleteFileUtil.deleteFile(filePath, request);
		
		// 文件删除
		if (msg.equals("success")) {
			msg = adminService.deleteMusic(id);
		}
		
		return msg;
	}
	
	/**
	 * 新增博客
	 * @param blog
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addBlog")
	public int addBlog(Blog blog){
		
		int id = adminService.addBlog(blog);
		
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
	public HashMap<String, Object> uploadContent(@RequestParam("imgFile") MultipartFile file, @PathVariable("id")Integer id, HttpServletRequest request) throws IllegalStateException, IOException {
		
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
	@RequestMapping("/uploadCover")
	public String uploadCover(@RequestParam("imgFile") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		
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
		
		String msg = adminService.updateBlog(blog);
		
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
		
		String msg = adminService.updateBlogNoTime(blog);
		
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
			msg = adminService.deleteBlogCover(id);
		}
		
		return msg;
	}
	
	
}



