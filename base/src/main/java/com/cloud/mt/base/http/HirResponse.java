package com.cloud.mt.base.http;

import com.cloud.mt.base.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Created by kwy on 2019/7/1.
 * 支持正常与特殊异常情况（即同时返回对象与异常信息的情况）的响应
 */
public class HirResponse {
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String code;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String msg;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Object data;

	public static HirResponse error() {
		return error(ErrorCode.SYS_UNKNOWN_ERROR.getCode(), "未知异常，请联系管理员");
	}

	public static HirResponse error(String msg) {
		return error(ErrorCode.SYS_UNKNOWN_ERROR.getCode(), msg);
	}

	public static HirResponse error(String code, String msg) {
		HirResponse r = new HirResponse();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static HirResponse ok(String msg) {
		HirResponse r = new HirResponse();
		r.setMsg(msg);
		return r;
	}

	public static HirResponse ok(Object data) {
		HirResponse r = new HirResponse();
		r.setData(data);
		return r;
	}

	public static HirResponse ok() {
		return new HirResponse();
	}

	public String getCode() {
		return this.code;
	}

	public HirResponse setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return this.msg;
	}

	public HirResponse setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return this.data;
	}

	public HirResponse setData(Object data) {
		this.data = data;
		return this;
	}

}
