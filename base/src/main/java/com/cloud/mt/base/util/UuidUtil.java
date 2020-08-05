package com.cloud.mt.base.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

@Slf4j
public class UuidUtil {
	
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	public static String get15UUID(){
		Random r = new Random();
		try {
			Thread.sleep(r.nextInt(10));
		} catch (InterruptedException e) {
			log.error("!-ERROR-! {}",e) ;
		}
		//13为时间
		String uuid = System.currentTimeMillis()+"";
		int random1 = r.nextInt(10);
		int random2 = r.nextInt(10);
		
		return uuid + random1 + random2;
	}
}
