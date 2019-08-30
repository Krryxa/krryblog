package com.krry.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.krry.entity.Params;
import com.krry.service.IPartService;

/**
 * Controller
 * controller层，作为请求转发
 * @author krry
 *
 */
@Controller
@RequestMapping("/part")
public class PartController {
	
	@Autowired
	private IPartService partService;
	
	
	/**
	 * 根据标签查询已发布的博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogByTag")
	public HashMap<String, Object> getBlogByTag(Params param){
		
		HashMap<String, Object> resData = partService.getBlogByTag(param);
		
		return resData;
	}
	
	
	/**
	 * 搜索已发布的博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogBykeyword")
	public HashMap<String, Object> getBlogBykeyword(Params param){
		
		HashMap<String, Object> resData = partService.getBlogBykeyword(param);
		
		return resData;
	}
	
	/**
	 * 查询相关链接 / 关于我
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLinkOrAbout")
	public HashMap<String, Object> getLinkOrAbout(HttpServletRequest request){
		
		String title = request.getParameter("title");
		HashMap<String, Object> resData = partService.getLinkOrAbout(title);
		
		return resData;
	}
	
	
	/**
	 * 查询音乐列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMusic")
	public HashMap<String, Object> getMusic(Params param){
		
		HashMap<String, Object> resData = partService.getMusic(param);
		
		return resData;
	}
	
	
}



