package com.sakura.security.config;

import com.sakura.security.component.PermitAllUrlProperties;
import com.sakura.security.component.SakuraUserAuthenticationConverter;
import com.sakura.security.component.ResourceAuthExceptionEntryPoint;
import com.sakura.security.component.SakuraBearerTokenExtractor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mr.zhou
 * 资源服务器配置
 * 1. 支持remoteTokenServices 负载均衡
 * 2. 支持获取用户全部信息
 * 3. 接口对外暴露，不校验 Authentication Header 头
 */
@Slf4j
public class SakuraResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	@Autowired
	protected RemoteTokenServices remoteTokenServices;

	@Autowired
	private AccessDeniedHandler pigAccessDeniedHandler;

	@Autowired
	private PermitAllUrlProperties permitAllUrl;

	@Autowired
	private RestTemplate lbRestTemplate;

	@Autowired
	private SakuraBearerTokenExtractor sakuraBearerTokenExtractor;

	/**
	 * 默认的配置，对外暴露
	 * @param httpSecurity
	 */
	@Override
	@SneakyThrows
	public void configure(HttpSecurity httpSecurity) {
		// 允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
		httpSecurity.headers().frameOptions().disable();

		//公开权限接口默认放行
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
				.authorizeRequests();
		permitAllUrl.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

		registry.anyRequest().authenticated()
				.and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		UserAuthenticationConverter userTokenConverter = new SakuraUserAuthenticationConverter();
		accessTokenConverter.setUserTokenConverter(userTokenConverter);

		//设置access_token校验地址（yml文件中配置）
		remoteTokenServices.setRestTemplate(lbRestTemplate);
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter);

		resources
				.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
				.tokenExtractor(sakuraBearerTokenExtractor)
				.accessDeniedHandler(pigAccessDeniedHandler)
				.tokenServices(remoteTokenServices);
	}

}
