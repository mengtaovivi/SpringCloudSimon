package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.mt.base.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 串案案件信息表
 *
 * @author liuwq
 */
@SuppressWarnings("serial")
@TableName("c_case_series")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "串案案件信息表")
public class CaseSeries extends BaseModel<CaseSeries> {

	@ApiModelProperty(value = "案件Id", hidden = false)
	@TableId(value = "id", type = IdType.INPUT)
	private String id;

	@ApiModelProperty(value = "案件编号", required = true)
	@TableField("c_ajbh")
	@NotBlank(message = "案件编号不能为空")
	private String cAjbh;

	@ApiModelProperty(value = "案件名称", required = true)
	@TableField("c_ajmc")
	@NotBlank(message = "案件名称不能为空")
	private String cAjmc;

	@ApiModelProperty(value = "关联案件id", required = true)
	@TableField("case_id")
	private String caseId;

}
