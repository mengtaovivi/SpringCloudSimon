package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mt.base.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @since 2019-11-06
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("JZMLWJ")
public class JzmlwjEntity extends BaseModel<JzmlwjEntity> implements Serializable {

	/**
	 * 卷宗编号
	 */
	@TableField("JZBH")
	private String jzbh;

	/**
	 * 目录编号
	 */
	@TableField("MLBH")
	private String mlbh;

	/**
	 * 文件序号
	 */
	@TableField("WJXH")
	private String wjxh;

	/**
	 * 文件名称
	 */
	@TableField("WJMC")
	private String wjmc;

	/**
	 * 文件路径
	 */
	@TableField("WJLJ")
	private String wjlj;

	/**
	 * 文件显示名称
	 */
	@TableField("WJXSMC")
	private String wjxsmc;

	/**
	 * 文件后缀
	 */
	@TableField("WJHZ")
	private String wjhz;

	/**
	 * 文件开始页
	 */
	@TableField("WJKSY")
	private String wjksy;

	/**
	 * 文件结束页
	 */
	@TableField("WJJSY")
	private String wjjsy;

	/**
	 * 文件顺序号
	 */
	@TableField("WJSXH")
	private String wjsxh;

	/**
	 * 部门受案号
	 */
	@TableField("BMSAH")
	private String bmsah;

	/**
	 * 单位编码
	 */
	@TableField("DWBM")
	private String dwbm;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	/**
	 * 关联的fileId
	 */
	@TableField("FILE_ID")
	private String fileId;

	/**
	 * 文件状态
	 */
	@TableField("STATUS")
	private String status;

	//关联的案件id
	@TableField("case_id")
	private String caseId;

	//文件类型.0：判决书，1:申诉意见书，2：证据文件
	@TableField("C_FILE_TYPE")
	private String cFileType;

	//是否展示导出列表
	@TableField("SFZSDCLB")
	private String sfzsdclb;

	//目录名称
	@TableField("MLMC")
	private String mlmc;
}
