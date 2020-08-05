package com.cloud.mt.websocket.model;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author simon
 * @Description 发送给前端的消息
 * @Date 17:58 2020/7/28
 * @Param
 * @return
 **/
@SuppressWarnings("serial")
@Data
@Accessors(chain = true)
public class OnlineMessageEntry implements Serializable {
	/** 消息来源 */
	private String fromId;
	/** 消息目标 */
	private String toId;
	/** 消息内容 */
	private String content;
}