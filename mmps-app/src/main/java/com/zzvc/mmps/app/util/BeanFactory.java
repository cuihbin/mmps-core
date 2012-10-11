package com.zzvc.mmps.app.util;

import java.util.Map;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring context manage utility
 * @author CHB
 *
 */
public class BeanFactory {
	private static final String[] configLocations = {"classpath*:/applicationContext-*.xml", "classpath*:/applicationContext.xml"};
	private static final AbstractXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(configLocations);
	
	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}
	
	public static Map getBeansOfType(Class type) {
		return beanFactory.getBeansOfType(type);
	}
	
	public static void start() {
		beanFactory.start();
	}
	
	public static void close() {
		if (beanFactory != null) {
			beanFactory.close();
		}
	}
	
}
