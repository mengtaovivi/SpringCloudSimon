package com.cloud.mt.websocket.controller;

import com.cloud.mt.base.context.SpringContextHolder;
import com.cloud.mt.websocket.model.OnlineMessageEntry;
import com.cloud.mt.websocket.service.OnlineMessageService;
import com.cloud.mt.websocket.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

/**
 * <h3>在线消息 WebSocket服务器端</h3><br>
 */
@ServerEndpoint(value = "/ws/online-message/{identity}", configurator = OnlineMessageServer.CustomSpringConfigurator.class)
@Component
@Slf4j
public class OnlineMessageServer {

	@Autowired
	private OnlineMessageService onlineMessageService;

	@Autowired
	private OnlineUserService onlineUserService;

	/**
	 * 建立连接成功调用
	 *
	 * @param session  Socket会话
	 * @param identity 用户身份标识
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "identity") String identity) {
		String userId = onlineUserService.getUserIdBy(identity);
		if (userId == null) {
			log.info("Invalid user identity " + identity);
			return;
		}
		onlineMessageService.sign(session, userId);
	}

	/**
	 * 收到客户端信息
	 *
	 * @param session Socket会话
	 * @param message 客户端信息
	 */
	@OnMessage
	public void onMessage(Session session, String message) {
		// TODO 目前不需要该功能
		System.out.println(message);
		OnlineMessageEntry on = new OnlineMessageEntry();
		on.setFromId("1") ;
		on.setToId("2") ;
		on.setContent(message) ;
		onlineMessageService.send(on);
	}

	/**
	 * 出现异常时调用被调用
	 *
	 * @param session   Socket会话
	 * @param throwable 异常
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error("!", throwable);
	}

	/**
	 * 关闭连接时调用
	 *
	 * @param session Socket会话
	 */
	@OnClose
	public void onClose(Session session) {
		onlineMessageService.close(session);
	}

	/**
	 * <h3>自定义装配器</h3><br>
	 * 默认情况ServerEndpoint是单例模式，通过自定义装配器使ServerEndpoint由Spring容器托管（单例模式）<br>
	 *
	 * @see javax.websocket.server.ServerEndpointConfig.Configurator
	 * @see org.springframework.web.socket.server.standard.SpringConfigurator
	 */
	public static class CustomSpringConfigurator extends ServerEndpointConfig.Configurator {
		public <T extends Object> T getEndpointInstance(Class<T> requiredType) {
			return SpringContextHolder.getBean(requiredType);
		}
	}

}
