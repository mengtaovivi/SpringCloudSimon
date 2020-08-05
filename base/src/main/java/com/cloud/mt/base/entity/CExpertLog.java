package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 叶成浪
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("c_expert_log")
public class CExpertLog extends Model<CExpertLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主表ID
	 */
	@TableId(value = "ID", type = IdType.AUTO)
	private Long id;

	/**
	 * 案件ID
	 */
	@TableField("AJ_ID")
	private String ajId;

	/**
	 * 操作用户姓名
	 */
	@TableField("TRUE_NAME")
	private String trueName;

	/**
	 * 操作用户ID
	 */
	@TableField("USER_ID")
	private String userId;

	/**
	 * 操作时间
	 */
	@TableField("CREATE_TIME")
	private Date createTime;

	/**
	 * 操作类型 1接受，2拒绝，3回避，4，系统自动生成替换专家
	 */
	@TableField("METHOD")
	private Integer method;

	/**
	 * 用户标识
	 */
	@TableField("USER_AGENT")
	private String userAgent;

	/**
	 * 操作的IP
	 */
	@TableField("USER_IP")
	private String userIp;

	/**
	 * 操作内容
	 */
	@TableField("CONTENT")
	private String content;

	/**
	 * 请求路径
	 */
	@TableField("URL")
	private String url;

	/**
	 * 应用平台 0.内网 1.外网  11.小程序
	 */
	@TableField("PLATFORM_TYPE")
	private Integer platformType;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
