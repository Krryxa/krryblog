package com.krry.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;


/**
 * 文件删除类
 * DeleteFileUtil
 * @author krry
 * @version 1.0.0
 *
 */
public class DeleteFileUtil {
	
	/**
	 * 删除文件
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




