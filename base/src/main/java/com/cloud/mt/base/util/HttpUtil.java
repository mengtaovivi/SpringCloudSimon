package com.cloud.mt.base.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP工具类
 * @author Louis
 * @date Oct 29, 2018
 */
public class HttpUtil {

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	/**
	 * 获取HttpServletResponse对象
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 输出信息到浏览器
	 * @param response
	 * @throws IOException
	 */
	public static void print(HttpServletResponse response,String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(msg);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
	}
	
}
