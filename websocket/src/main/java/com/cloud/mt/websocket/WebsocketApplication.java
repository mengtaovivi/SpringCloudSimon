package com.cloud.mt.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Author simon
 * @Description 主入口
 * @Date 16:43 2020/7/28
 * @Param
 * @return
 **/
@SpringBootApplication(scanBasePackages = "com.cloud.mt", exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.cloud.mt.base","com.cloud.mt.websocket.service", "com.cloud.mt.websocket.config"
		, "com.cloud.mt.websocket.controller",})
public class WebsocketApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

}
