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

@TableName("c_file_content")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="案件咨询和附件关联表内容")
public class FileContent extends Model<FileContent> {
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type = IdType.INPUT)
	@ApiModelProperty(value = "文件内容id")
    private String id;
	
	//文件内容
	@ApiModelProperty(value = "文件内容")
	private String textContent;
	
	@ApiModelProperty(value = "脱敏痕迹内容")
	@TableField(value = "TEXT_TMHJ_CONTENT")
	private String textTmhjContent;
	
	@ApiModelProperty(value = "是否保存过。1：保存过，0：没保存，若保存过，则在只能脱敏时不更新")
	@TableField(value = "SF_SAVE")
	private String sfSave;
	
}
