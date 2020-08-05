package com.cloud.mt.base.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class URIUtil {

	/**
	 * 重构 URL。map中的参数将与url地址中的参数进行匹配，若参数不存在，那么附加参数在url后面；若参数存在，那么将更新该参数。
	 * <p>
	 *     如：url: user/saveUser.htm?name=hy&pwd=123456 | map: {name=huyang,sex=1} <br />
	 *     返回结果： user/saveUser.htm?name=huyang&pwd=123456&sex=1
	 * </p>
	 * 注意：
	 * <ul>
	 *     <li>url, params 不可为空。若为空，原样返回url</li>
	 *     <li>url中 ? 号只能作为参数与请求地址的分隔符出现，否则可能产生不可预料的错误</li>
	 *     <li>该方法对url参数只做更新和新增，不做删除操作</li>
	 * </ul>
	 * @param url 需要重构的URL
	 * @param params 需要更新或添加的参数
	 * @return
	 */
	public static String rebuildURI(String url, Map<String, String> params) {
		// 定义ccm变量，用于拼接参数
		StringBuilder ccmsb = new StringBuilder();
		// 必要参数为空
		if(org.apache.commons.lang.StringUtils.isBlank(url) || null == params || params.size() == 0) {
			return url;
		}

		// 是否存在 ? 符号
		if(url.indexOf("?") == -1) {
			url += "?";
		}

		String relUrl = url.substring(0, url.lastIndexOf("?"));
		String urlParam = url.substring(url.lastIndexOf("?")+1, url.length());

		Map<String, String> urlParamMap = new LinkedHashMap<String, String>(params.size());
		// 若存在参数
		if(urlParam.indexOf("=") != -1 || urlParam.indexOf("&") != -1) {
			List<String> paramList = Arrays.asList(urlParam.split("&"));
			for (String param : paramList) {
				String key = param.split("=")[0];
				String value = param.split("=")[1];
				urlParamMap.put(key, value);
			}
		}

		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			urlParamMap.put(entry.getKey(), entry.getValue());
		}

		List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
		// 拼接参数
		if(urlParamMap != null || urlParamMap.size() != 0) {
			Iterator<Map.Entry<String, String>> ite = urlParamMap.entrySet().iterator();
			while(ite.hasNext()) {
				Map.Entry<String, String> entry = ite.next();
				if(org.apache.commons.lang.StringUtils.isNotBlank(entry.getValue())) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				/*ccmsb.append(entry.getKey());
				ccmsb.append("=");
				ccmsb.append(entry.getValue());
				if(ite.hasNext()) {
					ccmsb.append("&");
				}*/
			}
		}

		//String resultUrl = urlParamMap.size() == 0 ? relUrl : relUrl + "?" + ccmsb.toString();
		String resultUrl = "";
		try {
			resultUrl = urlParamMap.size() == 0 ? relUrl : relUrl + "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultUrl;
	}

	/**
	 * 获取请求中的 level 级域名，如 baidu.com、127.0.0.1、localhost
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request, int level) {
		if(null == request) {
			throw new NullPointerException("获取" + level + "级域名时出现异常，请求不可为空");
		}

		String url = request.getRequestURL().toString();

		String domain = url.substring(url.indexOf("/") + 2, url.length());

		if(-1 != domain.indexOf("/")) {
			domain = domain.substring(0, domain.indexOf("/"));
		}

		if(-1 != domain.indexOf(":")) {
			domain = domain.substring(0, domain.indexOf(":"));
		}

		// 比如 localhost
		if(-1 == domain.indexOf(".") || level <= 0) {
			return domain;
		}

		String[] part = domain.split("\\.");
		if(part.length >= 2) {
			if(part.length == 4) {
					boolean ipv4 = StringUtils.isNumeric(part[0]) && StringUtils.isNumeric(part[1])
							&& StringUtils.isNumeric(part[2]) && StringUtils.isNumeric(part[3]);
					if(ipv4) {
						return domain;
					}
			}

			if(level > part.length - 1) {
				throw new UnsupportedOperationException("无法解析 " + domain + " 为" + level + "级域名");
			}

			int start = part.length - level - 1;
			StringBuilder domainBuilder = new StringBuilder();
			for(int i = start; i < part.length; i ++) {
				domainBuilder.append(part[i]);
				if(i < part.length - 1) {
					domainBuilder.append(".");
				}
			}

			domain = domainBuilder.toString();
			return domain;
		}

		return null;
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");

		// pc ajax请求
		if (StringUtils.isNotEmpty(requestedWith) && StringUtils.equals(requestedWith, "XMLHttpRequest")) {
			return true;
		}

		return false;
	}

}
