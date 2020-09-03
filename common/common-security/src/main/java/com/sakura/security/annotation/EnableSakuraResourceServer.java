package com.sakura.security.annotation;

import com.sakura.security.annotation.component.SakuraResourceServerAutoConfiguration;
import com.sakura.security.annotation.component.SakuraSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author Mr.zhou
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ SakuraResourceServerAutoConfiguration.class, SakuraSecurityBeanDefinitionRegistrar.class })
public @interface EnableSakuraResourceServer {

}
