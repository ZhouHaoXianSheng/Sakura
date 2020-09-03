package com.sakura.security.annotation.component;

import com.sakura.common.core.constant.SecurityConstants;
import com.sakura.security.config.SakuraResourceServerConfigurerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import static com.sakura.common.core.constant.SecurityConstants.RESOURCE_SERVER_CONFIGURER;

/**
 * @author Mr.zhou
 * 主要目的是把SakuraResourceServerConfigurerAdapter注册为Bean
 */
@Slf4j
public class SakuraSecurityBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * 根据注解值动态注入资源服务器的相关属性
	 * @param metadata 注解信息
	 * @param registry 注册器
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// 由于这个包最终是要被注解注入的，而且这是一个common所以是被其他服务引用的，
		// 在注册bean的时候要先判断是否本地是否已经存在资源服务器配置了
		if (registry.isBeanNameInUse(RESOURCE_SERVER_CONFIGURER)) {
			log.warn("本地存在资源服务器配置，覆盖默认配置:" + SecurityConstants.RESOURCE_SERVER_CONFIGURER);
			return;
		}

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SakuraResourceServerConfigurerAdapter.class);
		registry.registerBeanDefinition(SecurityConstants.RESOURCE_SERVER_CONFIGURER, beanDefinition);

	}

}
