package com.cloud.mt.websocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author simon
 * @Description 在线用户服务
 * @Date 17:57 2020/7/28
 * @Param
 * @return
 **/
@Service
@Slf4j
public class OnlineUserService {

	// ==============================Fields===========================================
	// ...

	// ==============================Methods==========================================

	/**
	 * 查询用户ID
	 *
	 * @param identity 用户标识(可能是token也可能是openId)
	 * @return 用户ID
	 */
	public String getUserIdBy(String identity) {
		Assert.notNull(identity, "标识不能为空");
		// Map<String, OnlineSession> sessionPools = new ConcurrentHashMap<>();
		// ├ 1、判断是token 还是 openId
		// ├ 2、查询用户ID
		// │ ├ 2.1 根据token
		// │ └ 2.1 根据 openId
		// └3、返回用户ID
		return identity;
	}
}
