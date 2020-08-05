package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mt.base.base.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 卷宗目录实体类
 *
 * @author Hua-cloud
 */

@SuppressWarnings("serial")
@TableName("jzml")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value = "卷宗目录实体类")
public class JzmlEntity extends BaseModel<JzmlEntity> {
	// 卷宗编号
	@TableField("JZBH")
	private String jzbh;

	// 目录编号
	@TableField("MLBH")
	private String mlbh;
	// 目录类型
	@TableField("MLLX")
	private String mllx;
	// 父目录编号
	@TableField("FMLBH")
	private String fmlbh;
	// 父目录名称
	@TableField("FMLMC")
	private String fmlmc;
	// 目录显示名称
	@TableField("MLXSMC")
	private String mlxsmc;
	// 目录详细信息
	@TableField("MLXX")
	private String mlxx;
	// 目录顺序号
	@TableField("MLSXH")
	private String mlsxh;
	// 部门受案号
	@TableField("BMSAH")
	private String bmsah;
	// 单位编码
	@TableField("DWBM")
	private String dwbm;

	@TableId(value = "ID", type = IdType.UUID)
	private String id;
	//状态
	@TableField("STATUS")
	private String status;

	@TableField("case_id")
	private String caseId;

	//文件类型.0：判决书，1:申诉意见书，2：证据文件
	@TableField("C_FILE_TYPE")
	private String cFileType;

	//参考Id
	@TableField("REFERENCE_ID")
	private String referenceId;


	@TableField("UPLOAD_STATUS")
	private String uploadStatus;

	//是否补充材料
	@TableField("SFBCCL")
	private String sfbccl;

	//是否展示导出列表
	@TableField("SFZSDCLB")
	private String sfzsdclb;
}
