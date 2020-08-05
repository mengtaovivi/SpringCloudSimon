package com.cloud.mt.base.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WxMssVo {

	private String touser;//openid2
	private String template_id;//模板id
	private Map<String,String> miniprogram;//小程序appid
	private Map<String, Map<String,String>> data;//数据

}
