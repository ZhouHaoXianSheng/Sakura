package com.example.demo.temp;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author Mr.zhou
 * 资源服务器对外直接暴露URL,如果设置context-path 要特殊处理
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {

	//正则表达式表示匹配所有{***}形式的内容
	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private ApplicationContext applicationContext;

	@Getter
	@Setter
	private List<String> urls = new ArrayList<>();

	/**
	 * 除了配置文件中的公开地址，@Inner注解也是允许所有权限的
	 */
	@Override
	public void afterPropertiesSet() {
		RequestMappingHandlerMapping mapping1 = applicationContext.getBean(RequestMappingHandlerMapping.class);
		RequestMappingHandlerMapping mapping2 = applicationContext.getBean(RequestMappingHandlerMapping.class);
		protopeTest bean1 = applicationContext.getBean(protopeTest.class);
		protopeTest bean2 = applicationContext.getBean(protopeTest.class);
		System.out.println(mapping1==mapping2);
		System.out.println(bean1==bean2);
	}

	/**
	 * 为了获取到RequestMappingHandlerMapping.class
	 * 实现ApplicationContextAware接口，一般是用在非Spring管理的类上
	 * RequestMappingHandlerMapping似乎也能使用@Autowaired，因为两者都注册成了组件
	 * @param context
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}

}
