package com.cloud.mt.base.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @Author simon
 * @Description 无需登录即可访问的URL集
 * @Date 18:06 2020/7/28
 * @Param
 * @return
 **/
public interface ReleaseURLConstant {

	/**
	 * 发送短信验证码
	 */
	String SEND_V_CODE = "/sms/sendVCode";

	/**
	 * 校验短信验证码
	 */
	String CHECK_V_CODE = "/sms/checkVCode";

	/**
	 * 用户注册
	 */
	String SAVE_USER = "/user/saveUser";

	/**
	 * 用户登录
	 */
	String USER_LOGIN = "/user/userLogin.do";

	/**
	 * 外部跳转到本地的地址请求
	 */
	String ADDRESS_JUMP = "/ajxx/getAjxx";
//    String WS_SERVERONE ="/ws/serverOne";

	/**
	 * 上传身份证
	 */
	String IDENTITY_CARD_RECOGNITION = "identityCardRecognition";
	String CHECK_IDENTITY_CARD = "checkIdentityCard";

	/**
	 * 上传律师证
	 */
	String UPLOAD_LAWYER_CERTIFICATE_FILE_COVER = "uploadLawyerCertificateFileCover";
	String UPLOAD_LAWYER_CERTIFICATE_FILE_INSIDE = "uploadLawyerCertificateFileInside";

	/**
	 * swagger-ui
	 */
	String SWAGGER = "/swagger-ui.html";

	String SWAGGERRESOURCE = "/swagger-resources/**";

	String WEBJARS = "/webjars/**";

	/**
	 * TODO 暂时全部放行接口，上线需要去掉
	 */
	String ALL_URL = "/**";

	//配置不需要过滤的地址 action
	String IGNOREFILTERURL_ACTION = "*_uas.action*";

	//配置不需要过滤的地址 jsp
	String IGNOREFILTERURL_JSP = "*i.jsp*";

	String CAS_DO = "_cas.do";

	String CASLOGIN_DO = "casLogin.do";

	String VALIDATETOKE_DO = "*validateToke.do";

	String INSERTUSER = "insertUser.do";

	String DELETEUSER_DO = "deleteUser.do";

	String ENBALEUSER_DO = "enableUser.do";

	String DISABLEUSER_DO = "disableUser.do";

	//获取所有领域接口
	String GET_FIELD = "/ci/getAllFields";
	/**
	 * ReleaseUrl集合
	 */
	List<String> RELEASE_URLS = Arrays.asList(GET_FIELD, ADDRESS_JUMP, WEBJARS, SWAGGERRESOURCE, SWAGGER, SEND_V_CODE, CHECK_V_CODE, SAVE_USER, USER_LOGIN, IDENTITY_CARD_RECOGNITION, CHECK_IDENTITY_CARD, UPLOAD_LAWYER_CERTIFICATE_FILE_COVER, UPLOAD_LAWYER_CERTIFICATE_FILE_INSIDE, IGNOREFILTERURL_ACTION, IGNOREFILTERURL_JSP, CAS_DO, CASLOGIN_DO, VALIDATETOKE_DO, INSERTUSER, DELETEUSER_DO, ENBALEUSER_DO, DISABLEUSER_DO);
}
