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
import com.krry.entity.Music;
import com.krry.entity.Params;
import com.krry.service.IPartService;
import com.krry.util.DeleteFileUtil;
import com.krry.util.UploadUtil;

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
	
	/**
	 * 上传音乐
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String krryupload(@RequestParam("musicFile") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		
		// 调用工具类完成上传
		HashMap<String, Object> map = UploadUtil.uploadMusic(file, request);
		
		Music music = new Music();
		music.setTitle((String) map.get("title"));
		music.setSize((String) map.get("size"));
		
		// 保存数据库
		int id = partService.addMusic(music);
		
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
			msg = partService.deleteMusic(id);
		}
		
		return msg;
	}
	
	
}



