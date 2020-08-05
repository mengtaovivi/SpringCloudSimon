package com.cloud.mt.base.wechat;

import com.alibaba.fastjson.JSON;
import com.cloud.mt.base.dto.WxMssVo;
import com.cloud.mt.base.dto.WxUnionidOpenidDTO;
import com.cloud.mt.base.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxTemplate {
	/**
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 * @Author simon
	 * @Description 获得用户关于公众号的详细信息
	 * @Date 13:27 2020/6/4
	 * @Param [openid2, appId2, appSecret2]
	 **/
	@SuppressWarnings("unchecked")
	public WxUnionidOpenidDTO getUserinfo(String openid2, String appId2, String appSecret2) {
		String access_token = getAccess_token(appId2, appSecret2);
		String userInfo = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + openid2 + "&lang=zh_CN");
		ObjectMapper mapper2 = new ObjectMapper();
		WxUnionidOpenidDTO wxUnionidOpenidDTO = null;
		try {
			wxUnionidOpenidDTO = mapper2.readValue(userInfo, WxUnionidOpenidDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wxUnionidOpenidDTO;
	}

	/**
	 * @return void
	 * @Author simon
	 * @Description 发送模板消息
	 * @Date 13:27 2020/6/4
	 * @Param [openid2, id, type, checkTime, appId2, appSecret2]
	 **/
	@SuppressWarnings("unchecked")
	public static void sendMsg(String openid2, String id, String type, String checkTime, String appId2, String appSecret2) {
		WxMssVo wx = new WxMssVo();//发送模板消息请求参数封装对象
		wx.setTouser(openid2);
		wx.setTemplate_id("1FePKYxjLfISyDLvuzDXjOmIk3QmG3EMbusfNQD76Lk");
		Map<String, String> miniprogram = new HashMap<>();
		miniprogram.put("appid", "你的小程序appid");
		miniprogram.put("pagepath", "pages/checked/checked?id=" + id);
		wx.setMiniprogram(miniprogram);
		Map<String, String> first = new HashMap<>();
		Map<String, String> keyword1 = new HashMap<>();
		Map<String, String> keyword2 = new HashMap<>();
		Map<String, String> remark = new HashMap<>();
		first.put("value", "您的" + type + "申请已通过审核");
		first.put("color", "#173177");
		keyword1.put("value", "申请通过");
		keyword1.put("color", "#173177");
		keyword2.put("value", checkTime);
		keyword2.put("color", "#173177");
		remark.put("value", "点击查看详情");
		remark.put("color", "#173177");
		Map<String, Map<String, String>> map = new HashMap<>();
		map.put("first", first);
		map.put("keyword1", keyword1);
		map.put("keyword2", keyword2);
		map.put("remark", remark);
		wx.setData(map);
		String jsonString = JSON.toJSONString(wx);
		String access_token = getAccess_token(appId2, appSecret2);
		String data = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token, jsonString);
		System.out.println(data);
	}

	/**
	 * @return java.lang.String
	 * @Author simon
	 * @Description 获得公众号access_token
	 * @Date 13:28 2020/6/4
	 * @Param [appId2, appSecret2]
	 **/
	@SuppressWarnings("unchecked")
	private static String getAccess_token(String appId2, String appSecret2) {
		//从缓存中获取access_token
		String access_token = CacheManager.get("access_token");
		if (access_token == null) {
			access_token = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId2 + "&secret=" + appSecret2);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> json;
			try {
				json = mapper.readValue(access_token, Map.class);
				access_token = json.get("access_token");
				//将access_token存入缓存，设置过期时间为两个小时
				CacheManager.set("access_token", access_token, 7200 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return access_token;
	}


}
