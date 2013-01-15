package com.zzvc.mmps.app.util;

import java.util.Map;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring context manage utility
 * 
 * @author cuihbin
 * 
 */
public class BeanFactory {
	/**
	 * http://static.springsource.org/spring/docs/2.0.x/reference/resources.html
	 * #resources-wildcards-in-path-other-stuff File name wildcard combined with
	 * classpath* does not work with resources in root of jars eg.
	 * "classpath*:/applicationContext-*.xml"
	 */
	private static final String[] configLocations = { "classpath*:/applicationContext.xml" };
	private static final AbstractXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(configLocations);

	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
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
