package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mt.base.base.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 案件咨询和附件关联id
 */
@TableName("c_file")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件咨询和附件关联表")
public class AjzxJoinFile extends BaseModel<AjzxJoinFile> {
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.INPUT)
	private String id;
	//案件id
	private String cAjid;
	//附件id(存放到服务器上的文件名以uuid的形式表示,此字段就是保存服务器的文件名)
	private String cFileid;
	//文件名称（不加后缀）
	private String cFilename;
	//文件路径（存放在服务器的文件路径）
	private String cFilepath;
	//文件后缀
	private String cFilexe;
	//是否被导出过
	private Integer cIsdown;
	//文件类型.0：判决书，1:申诉意见书，2：证据文件
	private String cFileType;
	//脱敏前的文件内容id
	private String oriTextId;
	//脱敏后的文件内容id
	private String tmTextId;
	//状态 1.未脱敏 2.脱敏中 3.已脱敏
	private Integer status;
	//已经查看到第几页
	private String hasFindPage;
	//目录编号
	private String mlbh;
	//文件开始页
	private String wjksy;
	//文件结束页
	private String wjjsy;
	//卷宗编号
	private String jzbh;
	//文件结束页
	private String wjxh;
	//卷宗编号
	private String bmsah;

}














