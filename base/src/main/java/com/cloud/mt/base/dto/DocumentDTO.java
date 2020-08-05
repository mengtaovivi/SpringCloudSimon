package com.cloud.mt.base.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author HAI
 * @Date 2019/7/19 9:48
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class DocumentDTO {

    @ApiModelProperty(notes = "文书id",value = "主键",example = "123")
    @TableId(value="case_id")
    private Integer caseId;

    @ApiModelProperty(notes = "案件名称")
    private String caseName;

    @ApiModelProperty(notes = "用户id")
    private Integer userId;

    @ApiModelProperty(notes = "机关名称")
    private String institutionName;

    @ApiModelProperty(notes = "文书名称")
    private String documentName;

    @ApiModelProperty(notes = "文书扩展名")
    private String documentExt;

    @ApiModelProperty(notes = "发送日期")
    private Date sendTime;

    @ApiModelProperty(notes = "签收状态 Y:为签收 N:为未签收")
    private String signStatus;

    @ApiModelProperty(notes = "签收日期")
    private Date signTime;

    @ApiModelProperty(notes = "签名图片id")
    private String signFileId;

    @ApiModelProperty(notes = "反馈信息")
    private String feedbackInfo;

    @ApiModelProperty(notes = "签名url")
    private String signUrl;

    @ApiModelProperty(notes = "文书url")
    private String documentUrl;

    @ApiModelProperty(notes = "文书文件id")
    private String documentFileId;

    @ApiModelProperty(notes = "身份证号码")
    private String identityNumber;

    /**
     * 文书编号 (工作网文书唯一标识符）
     */
    private transient String documentNumber;



}
