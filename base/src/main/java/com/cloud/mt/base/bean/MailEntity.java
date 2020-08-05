package com.cloud.mt.base.bean;

import lombok.Data;

import java.util.Map;

@Data
public class MailEntity {
	/**
	 * 邮箱账号
	 */
	private String name;
	/**
	 * 邮箱密码
	 */
	private String pwd;
	/**
	 * 邮箱服务器主机名
	 * QQ:smtp.qq.com;新浪:smtp.sina.com.cn;网易:smtp.163.com;阿里:smtp.mxhichina.com
	 */
	private String serverName;
	/**
	 * 发件人邮箱
	 */
	private String senderMailbox;
	/**
	 * 收件人邮箱
	 */
	private String recipientMailbox;
	/**
	 * 邮件标题
	 */
	private String title;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 附件地址类型
	 */
	private String filePathType;
	/**
	 * FilePathTypeEnum.LOCAL_FILE - 附件本地文件地址集合
	 */
	private String[] fileLocalAddress;
	/**
	 * FilePathTypeEnum.URL_FILE 附件url地址集合 key=url，value=文件名+后缀
	 */
	private Map<String, String> fileUrlAddress;

}
