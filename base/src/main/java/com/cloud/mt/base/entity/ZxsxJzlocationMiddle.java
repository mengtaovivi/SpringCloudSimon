package com.cloud.mt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 案件咨询和附件关联id
 */
@TableName("ZXSX_JZLOCATION_MIDDLE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="案件咨询事项和卷宗关联表")
public class ZxsxJzlocationMiddle extends Model<ZxsxJzlocationMiddle> {
	private static final long serialVersionUID = 1L;
	@TableId(value="id",type = IdType.UUID)
    private String id;
	
	
	@ApiModelProperty(value = "咨询事项id",required = true)
	@TableField(value = "zxsx_id")
	private String zxsxId;
	
	@ApiModelProperty(value = "jzmlwj实体id",required = true)
	@TableField(value = "wjid")
	private String wjid;
	
	@ApiModelProperty(value = "jzmlwj实体 名字",required = true)
	@TableField(value = "wjmc")
	private String wjmc;
	
	@ApiModelProperty(value = "jzml实体id",required = true)
	@TableField(value = "mlid")
	private String mlid;
	
	@ApiModelProperty(value = "jzml实体 名字",required = true)
	@TableField(value = "mlmc")
	private String mlmc;
	
	@ApiModelProperty(value = "案件id",required = true)
	@TableField(value = "case_id")
	private String caseId;
	
	
	@ApiModelProperty(value = "排序",required = true)
	@TableField(value = "sort")
	private Integer sort;
	
}