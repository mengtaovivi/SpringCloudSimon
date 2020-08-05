package com.cloud.mt.websocket.service;

import com.alibaba.fastjson.JSON;
import com.cloud.mt.websocket.config.session.OnlineSessionPools;
import com.cloud.mt.websocket.model.OnlineMessageEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @Author simon
 * @Description 在线信息服务
 * @Date 17:57 2020/7/28
 * @Param
 * @return
 **/
@Service
@Slf4j
public class OnlineMessageService {

	// ==============================Fields===========================================
	@Autowired
	private OnlineSessionPools onlineSessionPools;

	// ==============================Methods==========================================

	/**
	 * 发送信息（只有用户登录的情况才会接收到信息）
	 *
	 * @param element 发送的信息元素
	 */
	public void send(OnlineMessageEntry element) {
		String toId = element.getToId();
		String message = JSON.toJSONString(element);
		for (Session session : onlineSessionPools.findByUserId(toId)) {
			sendMessage(session, message);
		}
	}

	/**
	 * 注册会话
	 *
	 * @param session WebSocket会话
	 * @param userId  绑定的用户ID
	 */
	public void sign(Session session, String userId) {
		onlineSessionPools.put(session, userId);
	}

	/**
	 * 移除会话
	 *
	 * @param session 移除的Socket会话
	 */
	public void close(Session session) {
		onlineSessionPools.remove(session);
	}

	/**
	 * 发送信息
	 *
	 * @param session 会话
	 * @param message 信息
	 */
	private static void sendMessage(Session session, String message) {
		if (session != null) {
			synchronized (session) {
				if (session.isOpen()) {
					try {
						session.getBasicRemote().sendText(message);
					} catch (IOException e) {
						log.error("！", e);
					}
				}
			}
		}
	}
}
