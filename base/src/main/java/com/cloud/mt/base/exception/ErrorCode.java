package com.cloud.mt.base.exception;

/**
 * Created by kwy on 2019/7/1.
 * 错误代码
 */
public enum ErrorCode {
    /* 以下定义权限系统的错误代码 */
    SYS_UNKNOWN_ERROR("SYS_UNKNOWN_ERROR", "未知错误"),
    SYS_BANK_NULL("SYS_BANK_NULL", "输入账户不能为空"),
    SYS_UNKNOWN_CHEHUI("SYS_UNKNOWN_CHEHUI", "您的咨询案件已被检察官撤回/终止，已不需要进行咨询，感谢您的支持"),
    SYS_PASSWORD_NULL("SYS_PASSWORD_NULL", "用户密码不能为空"),
    SYS_NO_ACCOUNT("SYS_NO_ACCOUNT", "系统暂时没有该用户"),
    SYS_PARAMISNUMM("SYS_PARAMISNUMM", "参数错误"),
    SYS_NOREPLAY_ERROR("SYS_NOREPLAY_ERROR", "接受的专家没有全部回复完，不可结束咨询"),
    SYS_VALIDATION_ERROR("SYS_VALIDATION_ERROR", "系统校验异常"),
    SYS_USER_LOGIN_ID_REPEAT("SYS_USER_LOGIN_ID_REPEAT", "该用户已经被占用！"),
    SYS_USER_MAJOR_ERROR("SYS_USER_MAJOR_ERROR", "系统中没有该主号号码，请重新填写！"),
    SYS_USER_MAJOR_EMPETY("SYS_USER_MAJOR_EMPETY", "主号码没有填写，请填写"),
    SYS_USER_REGISTER_ERROR("SYS_USER_REGISTER_ERROR", "新增或修改用户信息失败！"),
    SYS_USER_NOT_FOUND("SYS_USER_NOT_FOUND", "找不到该用户!用户ID=%s"),
    MAIN_USER_CAN_NOT_DELETE("MAIN_USER_CAN_NOT_DELETE", "主用户不允许删除"),
    SYS_USER_NOT_LOGIN("SYS_USER_NOT_LOGIN","您尚未登录"),
    SYS_USER_OTHER_LOGIN("SYS_USER_OTHER_LOGIN","您的账号在其它地方已经登录，请重新登录！"),
    ACCOUNT_OLD_PASSWORD_INCORRECTNESS("ACCOUNT_OLD_PASSWORD_INCORRECTNESS","原密码错误"),
    SYS_OBJ_NOT_FOUND("SYS_OBJ_NOT_FOUND", "%s"),
    NO_DELETE_PERMISSION("NO_DELETE_PERMISSION", "无删除权限"),
    NO_OPERATION_PERMISSION("NO_OPERATION_PERMISSION", "无操作权限"),
    SYS_OBJECT_IS_NOT_NULL("SYS_OBJECT_IS_NOT_NULL", "%s不能为空"),
    SYS_OBJECT_IS_EXIST("SYS_OBJECT_IS_EXIST", "%s已经存在"),
    ENUM_FROM_CODE_BLANK("ENUM_FROM_CODE_BLANK", "code为空字符串"),
    MOBILE_EXIST("MOBILE_EXIST", "该手机号已注册"),
    DEPT_NOT_SETTING("DEPT_NOT_SETTING","用户“%s”没有设置机构，请联系管理员给该用户设置机构"),

    //PC端---短信登录
    SYS_USER_PHONE_NUMBER_ERROR("SYS_USER_PHONE_NUMBER_ERROR","用户名或者手机号错误"),


    //注册登录模块
    SYS_USER_ALREADY_REGISTER("SYS_USER_ALREADY_REGISTER","该微信用户已经注册"),
    SYS_USER_NOT_REGISTER("SYS_USER_NOT_REGISTER","该微信用户没有注册"),

    //Redis错误码
    REDIS_OPERATION_ERROR("REDIS_OPERATION_ERROR","redis操作异常"),


    // 邮件发送
    EMAIL_SEND_FAIL("EMAIL_SEND_FAIL","邮件发送失败"),

    // 短信模板
    SMS_OBJ_NOT_FOUND("SMS_OBJ_NOT_FOUND", "短信账号未开通"),
    SMS_ADDRESS_NOT_FOUND("SMS_ADDRESS_NOT_FOUND", "短信接口未设置"),
    SMS_ACCOUNT_NOT_SIGN("SMS_ACCOUNT_NOT_SIGN", "未设置短信签名"),
    SMS_TEMPLATE_TOO_LONG("CRM_SMS_TEMPLATE", "短信模板内容超出长度"),
    SMS_VCODE_CHECK_FAIL("SMS_VCODE_CHECK_FAIL", "验证码校验失败"),
    SMS_VCODE_CHECK_NOT_EQUAL("SMS_VCODE_CHECK_NOT_EQUAL", "验证码不匹配"),

   

    // ocr
    OCR_ERROR("OCR_ERROR","上传内容解析异常"),
    OCR_FORMAT_ERROR("OCR_FORMAT_ERROR","ocr数据格式转换失败"),

    // 微信
    WECHAT_XCX_AUTHORIZE_FAIL("WECHAT_XCX_AUTHORIZE_FAIL","微信小程序授权失败!"),

    //PDFzh转换失败
    PDF_TRANSTO_IMG_ERROR("PDF_TRANS_ERROR","pdf转换图片失败"),
    
    //咨询回复
    COMMENT_SAVE_ERROR("COMMENT_SAVE_ERROR","咨询意见提交失败，请再次尝试重新填写提交！"),
	DISCUSS_SAVE_ERROR("DISCUSS_SAVE_ERROR","消息发送失败请重新发送!"),
	ZXZJ_SAVE_ERROR("ZXZJ_SAVE_ERROR","随机推送咨询专家失败!"),
	HAS_CREATE_PRO_CASE("HAS_CREATE_PRO_CASE","该咨询已经创建过专业咨询，禁止再次创建"),
	NONE_RELE_NO_DELETE("NONE_RELE_NO_DELETE","已提交和已导出咨询无法删除!"),
	NOT_IMPORT_INFORMATION("NOT_IMPORT_INFORMATION","导出案件中包含草稿状态的案件，禁止导出"),
	HAS_NO_SAVE_FILE("HAS_NO_SAVE_FILE","存在未确认过的文件"),
	
	//zip包导入
	IMPORT_DATA_HAS_ERROR("IMPORT_DATA_HAS_ERROR","导入的案件%s可能存在以下问题：此案件只能由创建咨询案件的助理或者检察官本人导入。"),
	IMPORT_DATA_NOT_DIR("IMPORT_DATA_NOT_DIR","【%s】,该压缩包中不能包含文件夹"),
	IMPORT_DATA_NOT_IMAGEORPDF("IMPORT_DATA_NOT_IMAGEORPDF","zip包中只能上传pdf和图片"),
	IMPORT_DATA_MUST_INCLUDE_FILE("IMPORT_DATA_MUST_INCLUDE_FILE","zip包中必须包含数据"),
	
	
	//用户密码校验
	//格式不符合
	GESHI_NOT_FUHE("GESHI_NOT_FUHE","请输入8到24位密码，字符类型为大小写字母，数字，以及特殊符号"),
	REPEAT_CHAR_EG_THREE("REPEAT_CHAR_EG_THREE","重复字不能超过三位"),
	SUP_THREE_CONTINUE_NUM("SUP_THREE_CONTINUE_NUM","不能超过三个连续数字"),
	;
    
    
    

	
    private String code;
    private String msg;
    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
