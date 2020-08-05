package com.cloud.mt.base.enums;

/**
 * 成功返回码
 * @author 叶成浪
 *
 */
public enum SuccessCode {

	CODE_SUCCESS("200", "请求成功"),
	
	CODE_NO_ZXZJ("400", "该案件暂无专家,不能发布!"),
	/**
	 * 1、新案件（列表、详情界面）——“您的咨询案件已被检察官撤回/终止，已不需要进行咨询，感谢您的支持”
	 * 跳转到已办案件列表
	 */
	NEW_CASE_LIST_DETAIL_ERROR("1024", "您的咨询案件已被检察官撤回/终止，已不需要进行咨询，感谢您的支持"),
	/**
	 *2、在办案件列表（列表、详情界面）——“您的咨询案件已被检察官撤回/终止，已不需要进行咨询回复，感谢您的支持”
	 * 跳转到已办案件列表
	 */
	ING_CASE_LIST_DETAIL_ERROR("1025", "您的咨询案件已被检察官撤回/终止，已不需要进行咨询回复，感谢您的支持"),
	/**
	 * 3、讨论室（列表、详情界面）——“您的咨询案件已被检察官撤回/终止，您已自动退出当前讨论室，感谢您的支持”
	 * 跳转到讨论室列表
	 */
	TLS_CASE_LIST_DETAIL_ERROR("1026", "您的咨询案件已被检察官撤回/终止，您已自动退出当前讨论室，感谢您的支持"),
	CODE_FAIL("400","请求失败");
	
	private String code;

	private String message;

	SuccessCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
