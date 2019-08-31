package com.krry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krry.entity.Params;
import com.krry.entity.ResponseVal;
import com.krry.entity.User;
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
	 * 登录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public ResponseVal login(User user){
		
		ResponseVal resData = partService.login(user);
		
		return resData;
	}
	
	
	/**
	 * 根据标签查询已发布的博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogByTag")
	public ResponseVal getBlogByTag(Params param){
		
		ResponseVal resData = partService.getBlogByTag(param);
		
		return resData;
	}
	
	
	/**
	 * 搜索已发布的博客
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBlogBykeyword")
	public ResponseVal getBlogBykeyword(Params param){
		
		ResponseVal resData = partService.getBlogBykeyword(param);
		
		return resData;
	}
	
	/**
	 * 查询相关链接 / 关于我
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLinkOrAbout")
	public ResponseVal getLinkOrAbout(HttpServletRequest request){
		
		String title = request.getParameter("title");
		ResponseVal resData = partService.getLinkOrAbout(title);
		
		return resData;
	}
	
	
	/**
	 * 查询音乐列表
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMusic")
	public ResponseVal getMusic(Params param){
		
		ResponseVal resData = partService.getMusic(param);
		
		return resData;
	}
	
	
}



