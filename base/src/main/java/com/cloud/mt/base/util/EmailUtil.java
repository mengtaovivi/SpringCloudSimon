package com.cloud.mt.base.util;

import com.cloud.mt.base.bean.MailEntity;
import com.cloud.mt.base.exception.ErrorCode;
import com.cloud.mt.base.exception.ErrorCodeException;
import com.cloud.mt.base.type.FilePathTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * @author created by kwy on 2019/7/2
 * 邮件工具类，支持本地文件附件，url附件
 * todo applicaion-test.yml 的email 上线配置如主机号信息，善未提供
 */
//@Component
//@ConfigurationProperties(prefix = "email")
@Slf4j
public final class EmailUtil {
	/**
	 * 我的邮箱账号
	 */
	private static String name;
	/**
	 * 我的邮箱密码(QQ邮箱页面-设置-账户-生产授权码,就是密码)
	 */
	private static String pwd;
	/**
	 * 邮箱服务器主机号
	 */
	private static String serverName;
	/**
	 * 我的邮箱号
	 */
	private static String senderMailbox;

	private EmailUtil() {
	}

	@Value("${email.name}")
	public void setName(String name) {
		EmailUtil.name = name;
	}

	@Value("${email.pwd}")
	public void setPwd(String pwd) {
		EmailUtil.pwd = pwd;
	}

	@Value("${email.serverName}")
	public void setServerName(String serverName) {
		EmailUtil.serverName = serverName;
	}

	@Value("${email.senderMailbox}")
	public void setSenderMailbox(String senderMailbox) {
		EmailUtil.senderMailbox = senderMailbox;
	}

	public static void sendEmail(String recipientMailbox, String title, String content) {
		MailEntity mailEntity = getMailEntity(recipientMailbox, title, content);
		if (execute(mailEntity)) {
			throw new ErrorCodeException(ErrorCode.EMAIL_SEND_FAIL);
		}
	}


	/**
	 * 发送携带url附件文件的邮件
	 *
	 * @param recipientMailbox 对方邮箱号
	 * @param title            标题
	 * @param content          内容正文
	 * @param fileUrlAddress   url附件地址
	 */
	public static void sendUrlFileEmail(String recipientMailbox, String title, String content, Map<String, String> fileUrlAddress) {
		MailEntity mailEntity = getMailEntity(recipientMailbox, title, content);
		mailEntity.setFilePathType(FilePathTypeEnum.URL_FILE.toCode());
		mailEntity.setFileUrlAddress(fileUrlAddress);
		if (execute(mailEntity)) {
			throw new ErrorCodeException(ErrorCode.EMAIL_SEND_FAIL);
		}

	}

	/**
	 * 发送携带本地文件附件的邮件
	 *
	 * @param recipientMailbox 对方邮箱号
	 * @param title            标题
	 * @param content          内容正文
	 * @param fileLocalAddress 本地附件地址
	 */
	public static void sendLocalFileEmail(String recipientMailbox, String title, String content, String... fileLocalAddress) {
		MailEntity mailEntity = getMailEntity(recipientMailbox, title, content);
		mailEntity.setFilePathType(FilePathTypeEnum.LOCAL_FILE.toCode());
		mailEntity.setFileLocalAddress(fileLocalAddress);
		if (execute(mailEntity)) {
			throw new ErrorCodeException(ErrorCode.EMAIL_SEND_FAIL);
		}

	}

	/**
	 * @return 获取邮件对象
	 */
	private static MailEntity getMailEntity(String recipientMailbox, String title, String content) {
		MailEntity mailEntity = new MailEntity();
		mailEntity.setName(EmailUtil.name);
		mailEntity.setPwd(EmailUtil.pwd);
		mailEntity.setServerName(EmailUtil.serverName);
		mailEntity.setSenderMailbox(EmailUtil.senderMailbox);
		mailEntity.setRecipientMailbox(recipientMailbox);
		mailEntity.setTitle(title);
		mailEntity.setContent(content);
		return mailEntity;
	}

	/**
	 * 执行发送邮件
	 *
	 * @return 是否发送失败
	 */
	private static boolean execute(MailEntity mailEntity) {
		try {
			Properties props = new Properties();
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.host", mailEntity.getServerName());
			//设置发送邮箱端口
			props.put("mail.smtp.port", "465");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			//使用加密需要配置
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			props.put("mail.smtp.ssl.enable", "true");
			// 设置邮箱环境
			Session session = Session.getInstance(props);
			// 创建邮箱对象
			Message msg = new MimeMessage(session);
			// 标题
			msg.setSubject(mailEntity.getTitle());
			// 发送人的邮箱
			msg.setFrom(new InternetAddress(mailEntity.getSenderMailbox()));
			// 附件地址不为空,进行附件添加
			if (StringUtils.isNotBlank(mailEntity.getFilePathType())) {
				MimeMultipart mp = new MimeMultipart();
				BodyPart bodyPart = new MimeBodyPart();

				// 防止正文内容丢失，使用附件发送时，应该在这里添加正文内容
				bodyPart.setText(mailEntity.getContent());
				mp.addBodyPart(bodyPart);
				// 文件地址
				// 设置附件
				if (FilePathTypeEnum.LOCAL_FILE.toCode().equals(mailEntity.getFilePathType()) && mailEntity.getFileLocalAddress() != null && mailEntity.getFileLocalAddress().length > 0) {
					for (int i = 0; i < mailEntity.getFileLocalAddress().length; i++) {
						BodyPart bp = new MimeBodyPart();
						FileDataSource fds = new FileDataSource(mailEntity.getFileLocalAddress()[i]);
						bp.setDataHandler(new DataHandler(fds));
						bp.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
						mp.addBodyPart(bp);
					}
				} else if (FilePathTypeEnum.URL_FILE.toCode().equals(mailEntity.getFilePathType()) && mailEntity.getFileUrlAddress() != null && mailEntity.getFileUrlAddress().size() > 0) {
					Map<String, String> fileUrlAddressMap = mailEntity.getFileUrlAddress();
					for (String fileUrl : fileUrlAddressMap.keySet()) {
						BodyPart bp = new MimeBodyPart();
						String fileAllName = fileUrlAddressMap.get(fileUrl);
						URL url = new URL(fileUrl);
						bp.setDataHandler(new DataHandler(url));
						bp.setFileName(MimeUtility.encodeText(fileAllName, "UTF-8", "B"));
						mp.addBodyPart(bp);
					}
				}
				msg.setContent(mp);
				msg.saveChanges();
			} else {
				// 内容 不携带附件时 这里写正文
				msg.setText(mailEntity.getContent());
			}
			Transport transport = session.getTransport();
			// 后两个参数:发送人的邮箱用户名,发送人的邮箱密码/或者授权码.
			transport.connect(mailEntity.getServerName(), mailEntity.getName(), mailEntity.getPwd());
			// 收件人邮箱地址
			transport.sendMessage(msg, new Address[]{new InternetAddress(mailEntity.getRecipientMailbox())});
			transport.close();
			return false;
		} catch (Exception e) {
			log.info(e.getMessage());
//            System.out.println(e.getMessage());
			return true;
		}
	}

	/**
	 * 测试发邮件
	 */
//    @GetMapping("testSendEmail")
//    public void testSendEmail(){
//        // 验证码-无附件方式
//        /*EmailUtil.sendEmail("18819288184@sina.cn","广云物联修改账号密码验证码","您此次的验证码为："+9527);*/
//        EmailUtil.sendEmail("8492908880@qq.com","xxx账号密码验证码","您此次的验证码为："+9527);
//        // 正文 + 本地附件
//        /*EmailUtil.sendLocalFileEmail("kewy@hua-cloud.com.cn","广云物联修改账号密码验证码",
//                "您此次的验证码为："+9527,"D:/白痴.jpg","D:/人脸核身接入文档.pdf","D:/test.jpg");*/
//        // 正文 + url附件
//        /*Map<String, String> urlMap = new HashMap<>();
//        urlMap.put("http://172.16.119.186/group1/M00/00/01/rBB3ul0V6emAcCuUAADBO4OZuBY528.jpg","律师证.jpg");
//        urlMap.put("http://gss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8601a18b87d6277f21a24bc123381f30e824fc93.jpg","mm.jpg");
//        urlMap.put("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=907f6e689ddda144c5096ab282b6d009/dc54564e9258d1092f7663c9db58ccbf6c814d30.jpg","bilibili.jpg");
//
//        EmailUtil.sendUrlFileEmail("18819288184@sina.cn","广云物联修改账号密码验证码",
//                "您此次的验证码为："+9527, urlMap);*/
//    }
}
