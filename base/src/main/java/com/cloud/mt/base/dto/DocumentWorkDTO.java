package com.cloud.mt.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author HAI
 * @Date 2019/7/17 18:23
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class DocumentWorkDTO {

    @ApiModelProperty(notes = "文书id",value = "文书id",example = "123")
    private Integer documentId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "案件名称")
    private String caseName;

    @ApiModelProperty(notes = "案件类型")
    private String caseType;

    @ApiModelProperty(notes = "所属单位(机关名称)")
    private String institutionName;

    @ApiModelProperty(notes = "接收人姓名")
    private String receiverName;

    @ApiModelProperty(notes = "接收人电话号码")
    private String receiverPhone;

    @ApiModelProperty(notes = "接收人身份证号")
    private String receiverNumber;

    @ApiModelProperty(notes = "文书名称")
    private String documentName;

    @ApiModelProperty(notes = "文书文件id")
    private String documentFileId;

    @ApiModelProperty(notes = "发送状态")
    private String sendStatus;

    @ApiModelProperty(notes = "文书扩展名")
    private String documentExt;

    @ApiModelProperty(notes = "签收状态")
    private String signStatus;

    @ApiModelProperty(notes = "反馈信息")
    private String feedbackInfo;

    @ApiModelProperty(notes = "签收日期")
    private Date signTime;

    @ApiModelProperty(notes = "签名文件id")
    private String signFileId;

    @ApiModelProperty(notes = "发送日期")
    private Date sendTime;

    @ApiModelProperty(notes = "短信模块id")
    private Integer smsTemplateId;

    @ApiModelProperty(notes = "文书类型",value = "文书类型",example = "文书类型",required = true)
    private String documentType;

    /**
     * 文书编号 (工作网文书唯一标识符）
     */
    private String documentNumber;

}
