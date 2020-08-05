package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mt.base.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 案件咨询entity
 * @author Hua-cloud
 *
 */
@TableName("c_information")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="案件咨询表")
public class CaseConsulting extends BaseModel<CaseConsulting> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "案件Id",hidden = false)
	@TableId(value="id",type = IdType.INPUT)
    private String id;
	
	@ApiModelProperty(value = "案件编号",required = true)
    @NotBlank(message = "案件编号不能为空")
	private String cAjbh;
	
	@ApiModelProperty(value = "常规案件id",required = false)
	private String cCgajid;
	
	//案件类型，0：民事案件，1：行政案件
	@ApiModelProperty(value = "案件类型",required = true)
    @NotBlank(message = "案件类型不能为空")
	private String cSjlx;
	
	@ApiModelProperty(value = "案件名称",required = true)
    @NotBlank(message = "案件名称不能为空")
	private String cAjmc;
	
	//咨询类型，0：常规咨询，1：专业咨询
	@ApiModelProperty(value = "咨询类型",required = true)
    @NotBlank(message = "咨询类型不能为空")
	private String cCxlx;
	
	@ApiModelProperty(value = "咨询开始时间",hidden=true)
//    @NotBlank(message = "咨询开始时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone ="GMT+8",pattern = "yyyy-MM-dd")
	private Date cZxkssj;
	
	@ApiModelProperty(value = "咨询结束时间",hidden=true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone ="GMT+8",pattern = "yyyy-MM-dd")
	private Date cZxjssj;
	
	@ApiModelProperty(value = "案件状态",required = true)
    @NotBlank(message = "案件状态不能为空")
	private String cAjzt;
	
	@ApiModelProperty(value = "创建人id",hidden = true)
	private String createUser;
	
	@ApiModelProperty(value = "是否被删除，0:false,未被删除，1：true，已被删除",hidden = true)
	private String cIsdel;
	
	@ApiModelProperty(value = "是否创建过专业咨询 ，0：false,1:true",hidden = true)
	private String isCreateZyzx;
	
	@ApiModelProperty(value = "是否创建过聊天室 ，0：false,1:true",hidden = true)
	private String isGroup;
	
	@ApiModelProperty(value = "创建者的登录名",hidden = true)
	private String loginName;
	
	@ApiModelProperty(value = "创建者的真实姓名",hidden = true)
	private String realName;
	
	@ApiModelProperty(value = "案件原由",hidden = true)
	private String cAjyy;
	
	
	@ApiModelProperty(value = "第几次创建咨询",hidden = true)
	@TableField("times")
	private Integer times;
	
	@ApiModelProperty(value = "是否显示",hidden = true)
	@TableField("is_show")
	private String isShow;
	
	@ApiModelProperty(value = "部门受案号",hidden = true)
	@TableField("bmsah")
	private String bmsah;
	
	@ApiModelProperty(value = "案件类别名称",hidden = true)
	@TableField("AJLBMC")
	private String ajlbmc;
	
	@ApiModelProperty(value = "是否同步",hidden = true)
	@TableField("SFTB")
	private String sftb;
	
	@ApiModelProperty(value = "是否导出过咨询报告",hidden = true)
	@TableField("c_isimport")
	private String cIsimport;
	
	
	@ApiModelProperty(value = "案件领域",hidden = true)
	@TableField("c_field")
	private String cField;
	
	
	
	@ApiModelProperty(value = "副号创建用户id",hidden = true)
	@TableField("SUB_CREATE_USER_ID")
	private String subCreateUserId;
	
	
	
	@ApiModelProperty(value = "主号创建用户id",hidden = true)
	@TableField("SUB_CREATE_USER_LOGIN")
	private String subCreateUserLogin;
	
	
	@ApiModelProperty(value = "地区编码",hidden = true)
	@TableField("FPJCYMC")
	private String fpjcymc;
	
	
	@ApiModelProperty(value = "专家回复意见知否互相可见(1:true,0:false)",hidden = true)
	@TableField("SFHXKJ")
	private String sfhxkj;
	
	@ApiModelProperty(value = "身份",hidden = true)
	@TableField("IDENTITY_TYPE")
	private String identityType;
	
	@ApiModelProperty(value = "案件子状态,0-正常咨询完结，1-停止咨询",required = true)
	@TableField("c_child_ajzt")
	private String cChildAjzt;
	
	@ApiModelProperty(value = "终止咨询理由",required = true)
	@TableField("c_stop_case_reason")
	private String cStopCaseReason;
	
	@ApiModelProperty(value = "机构简称",required = true)
	@TableField("jgjc")
	private String jgjc;
	
	@ApiModelProperty(value = "编码",required = false)
	@TableField("number")
	private Integer number;
	
	@ApiModelProperty(value = "报告编码",required = false)
	@TableField("report_num")
	private String reportNum;
	
}
