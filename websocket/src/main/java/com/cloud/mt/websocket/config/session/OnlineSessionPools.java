package com.cloud.mt.websocket.config.session;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线会话池
 */
@Component
public class OnlineSessionPools {

	// ==============================Fields===========================================
	/**
	 * Session池
	 */
	private final Map<String, OnlineSession> sessionPools = new ConcurrentHashMap<>();
	/**
	 * 用户到SessionId的映射
	 */
	private final SetMultimap<String, String> userSessionMultimap = HashMultimap.create();
	/**
	 * 锁对象
	 */
	private final Object monitor = new byte[0];

	// ==============================Methods==========================================

	/**
	 * 注册会话
	 *
	 * @param session WebSocket会话
	 * @param userId  绑定的用户ID
	 */
	public void put(final Session session, final String userId) {
		OnlineSession element = new OnlineSession(session, userId);
		synchronized (monitor) {
			String sessionId = session.getId();
			sessionPools.put(sessionId, element);
			userSessionMultimap.put(userId, sessionId);
		}
	}

	/**
	 * 移除会话
	 *
	 * @param session 移除的Socket会话
	 */
	public void remove(final Session session) {
		String id = session.getId();
		synchronized (monitor) {
			OnlineSession element = sessionPools.remove(id);
			if (element != null) {
				String userId = element.getUserId();
				userSessionMultimap.remove(userId, id);
			}
		}
	}

	/**
	 * 根据用户查询WebSocket会话
	 *
	 * @param userId 用户ID
	 * @return WebSocket会话列表
	 */
	public List<Session> findByUserId(final String userId) {
		final List<Session> sessions = new ArrayList<>();
		synchronized (monitor) {
			Set<String> sessionIdSet = userSessionMultimap.get(userId);
			for (String sessionId : sessionIdSet) {
				OnlineSession element = sessionPools.get(sessionId);
				if (element != null) {
					sessions.add(element.getSession());
				}
			}
		}
		return sessions;
	}

	/**
	 * 根据会话查询用户
	 *
	 * @param session
	 * @return 用户ID
	 */
	public String getUserId(final Session session) {
		String sessionId = session.getId();
		OnlineSession element = sessionPools.get(sessionId);
		return element != null ? element.getUserId() : null;
	}
}
