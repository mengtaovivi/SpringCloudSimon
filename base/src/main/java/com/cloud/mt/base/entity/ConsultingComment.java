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
 * 案件咨询中的咨询事项明细
 *
 * @author Hua-cloud
 */

@TableName("c_zxsx")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "咨询事项明细表")
public class ConsultingComment extends BaseModel<ConsultingComment> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "咨询事项id", hidden = false)
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	@ApiModelProperty(value = "事项名称", required = true)
	@NotBlank(message = "事项名称不能为空")
	private String cSxmc;

	/**
	 * 如果改字段为空或者Null，问题为简单题，
	 * 如果字段不为空，则为选择题。
	 */
	@ApiModelProperty(value = "事项选项", required = false)
//    @NotBlank(message = "事项选项不能为空")
	private String cSxxx;


	@ApiModelProperty(value = "案件信息id", hidden = true)

	@TableField(value = "c_zxsxId")
	private String cZxsxId;

	@ApiModelProperty(value = "是否删除", hidden = true)
	private Integer cIsdel;

	@ApiModelProperty(value = "是否为选择题", hidden = true)
	private Integer cIschoose;

	@ApiModelProperty(value = "选择题选项的关联父id", hidden = false)
	private Integer cPid;


	@ApiModelProperty(value = "排序", hidden = false)
	private Integer sort;


	@ApiModelProperty(value = "咨询类型", hidden = false)
	private String zxlx;
}
