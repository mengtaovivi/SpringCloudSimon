package com.cloud.mt.base.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	// ==============================Fields===========================================
	private static final AtomicReference<ApplicationContext> REFERENCE = new AtomicReference<>();

	// ==============================Methods==========================================
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return REFERENCE.get().getBean(requiredType);
	}

	public static <T> T createBean(Class<T> beanClass) {
		return REFERENCE.get().getAutowireCapableBeanFactory().createBean(beanClass);
	}

	// ==============================OverrideMethods==================================
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		REFERENCE.set(applicationContext);
	}

	@Override
	public void destroy() throws Exception {
		REFERENCE.set(null);
	}
}
