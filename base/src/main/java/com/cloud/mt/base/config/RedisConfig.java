package com.cloud.mt.base.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author simon
 * @Description RedisConfig
 * @Date 18:03 2020/7/28
 * @Param
 * @return
 **/
@Configuration
public class RedisConfig {

	/**
	 * redisTemplate
	 * key: String
	 * value: Json
	 *
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);

		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
		//null值也进行序列化
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				/**
				 * 配置死循环
				 */
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue
		);
		fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		/** key采用String的序列化方式 */
		template.setKeySerializer(stringRedisSerializer);
		/** hash的key也采用String的序列化方式 */
		template.setHashKeySerializer(stringRedisSerializer);
		/** value序列化方式采用 fastJson */
		template.setValueSerializer(fastJsonRedisSerializer);
		/** hash的value序列化方式采用 fastJson */
		template.setHashValueSerializer(fastJsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
