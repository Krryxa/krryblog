package com.krry.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.json.JSONUtils;


/**
 * 文件上传类
 * UploadUtil
 * @author krry
 * @version 3.0.0
 *
 */
public class UploadUtil {
	
	/**
	 * 封面图片文件上传
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String singleupload(MultipartFile file, HttpServletRequest request) 
			throws IllegalStateException, IOException {
		
		if(!file.isEmpty()){
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			
			String oldName = file.getOriginalFilename();
			
			long size = file.getSize();

			// 优化文件大小
			String sizeString = formatSize(size);
			
			// 随机重命名 UUID
			String newFileName = generateFileName(oldName);
			
			// 日期路径
			String ymd = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String url = "upload/"+ymd;
			
			// 重新获取服务器的路径
			String dirPath = request.getSession().getServletContext().getRealPath(url);

			// 最终文件上传的对象
			File targetFile = new File(dirPath, newFileName); 
			
			// 获取父目录
			File pfile = new File(targetFile.getPath());
			// 判断如果目录不存在，先创建
			if(!pfile.exists()) pfile.mkdirs();
			
			// 文件传输
			file.transferTo(targetFile);
			
			map.put("oldName", oldName);// 文件原名称
			map.put("size", sizeString);
			map.put("name", newFileName);// 文件新名称
			map.put("url", url+"/"+newFileName);
			
			// 以json方式输出到页面
			return JSONUtils.toJSONString(map);
		} else {
			return null;
		}
	}

	/**
	 * markdown 图片文件上传
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static HashMap<String, Object> markdownUpload(MultipartFile file, HttpServletRequest request, Integer blogIndex) 
			throws IllegalStateException, IOException {
		
		if(!file.isEmpty()){
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			
			String title = file.getOriginalFilename();
			
			long size = file.getSize();

			// 优化文件大小
			String sizeString = formatSize(size);
			
			String url = "upload/content/" + blogIndex;
			
			// 重新获取服务器的路径
			String dirPath = request.getSession().getServletContext().getRealPath(url);

			// 最终文件上传的对象
			File targetFile = new File(dirPath, title); 
			
			// 获取父目录
			File pfile = new File(targetFile.getPath());
			// 判断如果目录不存在，先创建
			if(!pfile.exists()) pfile.mkdirs();
			
			// 文件传输
			file.transferTo(targetFile);
			
			map.put("title", title);// 文件原名称
			map.put("size", sizeString);
			map.put("url", url+"/"+title);
			
			// 以map方式输出到页面
			return map;
		} else {
			return null;
		}
	}
	
	/**
	 * 音乐文件上传
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static HashMap<String, Object> uploadMusic(MultipartFile file, HttpServletRequest request) 
			throws IllegalStateException, IOException {
		
		if(!file.isEmpty()){
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			
			String title = file.getOriginalFilename();
			
			long size = file.getSize();

			// 优化文件大小
			String sizeString = formatSize(size);
			
			String url = "music";
			
			// 重新获取服务器的路径
			String dirPath = request.getSession().getServletContext().getRealPath(url);

			// 最终文件上传的对象
			File targetFile = new File(dirPath, title); 
			
			// 获取父目录
			File pfile = new File(targetFile.getPath());
			// 判断如果目录不存在，先创建
			if(!pfile.exists()) pfile.mkdirs();
			
			// 文件传输
			file.transferTo(targetFile);
			
			map.put("title", title);// 文件原名称
			map.put("size", sizeString);
			map.put("url", url+"/"+title);
			
			// 以map方式输出到页面
			return map;
		} else {
			return null;
		}
	}
	
	/**
	 * 文件重命名
	 * @param fileName
	 * @param randomNum
	 * @param dataPattern
	 * @return
	 */
	public static String generateFileName(String fileName){

		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		
		String newFileName = UUID.randomUUID().toString()+extension;

		return newFileName;		
	}
	
	/**
	 * 将文件的字节数转换成文件的大小
	 * com.krry.uitl 
	 * 方法名：format
	 * @author krry 
	 * @param size
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String formatSize(long size){
		float fsize = size;
		String fileSizeString;
		if (fsize < 1024) {
			fileSizeString = String.format("%.2f", fsize) + "B"; //2f表示保留两位小数
		} else if (fsize < 1048576) {
			fileSizeString = String.format("%.2f", fsize/1024) + "KB";
		} else if (fsize < 1073741824) {
			fileSizeString = String.format("%.2f", fsize/1024/1024) + "MB";
		} else if (fsize < 1024 * 1024 * 1024) {
			fileSizeString = String.format("%.2f", fsize/1024/1024/1024) + "GB";
		} else {
			fileSizeString = "0B";
		}
		return fileSizeString;
	}
}




