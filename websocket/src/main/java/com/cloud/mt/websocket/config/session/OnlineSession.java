package com.cloud.mt.websocket.config.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * 在线会话
 */
@SuppressWarnings("serial")
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class OnlineSession implements Serializable {

	/**
	 * 会话ID
	 */
	private final String id;
	/**
	 * Socket会话
	 */
	private final Session session;
	/**
	 * 用户ID
	 */
	private final String userId;

	/**
	 * 构造函数（在线会话）
	 *
	 * @param session Socket会话
	 * @param userId  会话ID
	 */
	public OnlineSession(Session session, String userId) {
		this.session = session;
		this.userId = userId;
		this.id = session.getId();
	}
}