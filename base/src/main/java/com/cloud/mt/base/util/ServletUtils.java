package com.cloud.mt.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class ServletUtils {

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getIpAddr() {
		String ipAddress = getRequest().getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = getRequest().getRemoteAddr();
			if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error("!-ERROR-! {}",e) ;
				}

				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(',') > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(','));
		}

		return ipAddress;
	}
	/**
	 * 获取项目根路径
	 * linux服务器 项目路径就是 jar包的路径
	 * idea，eclipse工具启动项目，项目路径就是与target同级目录
	 * @return
	 */
	public static String getRootPath(){
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {

		}
		if (path == null || !path.exists()) {
			path = new File("");
		}
		String pathStr = path.getAbsolutePath();
		if(PlatformUtils.isLinux()) {
			pathStr = pathStr.replace("/target/classes", "");
		}else{
			pathStr = pathStr.replace("\\target\\classes","");
		}
		return  pathStr;
	}

}
