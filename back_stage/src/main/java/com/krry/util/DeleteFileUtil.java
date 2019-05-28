package com.krry.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.json.JSONUtils;


/**
 * 文件删除类
 * DeleteFileUtil
 * @author krry
 * @version 1.0.0
 *
 */
public class DeleteFileUtil {
	
	/**
	 * 删除博客封面
	 * @param filePath
	 * @param request
	 */
	public static String deleteFile(String filePath, HttpServletRequest request) {

		// 获取服务器路径
		String dirPath = request.getSession().getServletContext().getRealPath("/");

		File file = new File(dirPath, filePath);
        if(file.exists() && file.isFile()) {
        	file.delete();
        	
        	return "success";
        }
        return "fail";
	}
}




