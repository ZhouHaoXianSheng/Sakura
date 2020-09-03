package com.sakura.auth.config;

import com.sakura.common.core.constant.CacheConstants;
import com.sakura.common.core.constant.SecurityConstants;

import com.sakura.auth.component.SakuraWebResponseExceptionTranslator;
import com.sakura.security.service.SakuraClientDetailsService;
import com.sakura.security.service.SakuraUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Zhou
 * 认证服务器配置
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final DataSource dataSource;

	private final UserDetailsService userDetailsService;

	private final AuthenticationManager authenticationManager;

	private final RedisConnectionFactory redisConnectionFactory;

	/**
	 * 配置客户端的详细信息（此处使用jdbc存储在数据库中）
	 * 用于校验客户端
	 * @param clients
	 */
	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		SakuraClientDetailsService clientDetailsService = new SakuraClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	/**
	 * 配置令牌端点的安全约束，也就是哪些端点能访问，哪些不能访问
	 * 这个端点此处设置为直接访问
	 * @param oauthServer
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
	}

	/**
	 * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
	 * @param endpoints
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
				//端点允许的请求方式
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				//token存储
				.tokenStore(tokenStore())
				//token增强
				.tokenEnhancer(tokenEnhancer())
				//用户信息，此处应该是自动注入自定义的userDetailsService
				.userDetailsService(userDetailsService)
				//通常的实现是ProviderManager
				.authenticationManager(authenticationManager)
				.reuseRefreshTokens(false)
				//授权端点
				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
				//自定义错误处理（序列化）
				.exceptionTranslator(new SakuraWebResponseExceptionTranslator());
	}

	/**
	 * 将token存入Redis
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(CacheConstants.PROJECT_OAUTH_ACCESS);
		return tokenStore;
	}

	/**
	 * 我们可以从 OAuth2AccessToken中取出已经生成的信息，
	 * 然后在此基础上追加自己的信息
	 * @return
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			SakuraUser sakuraUser = (SakuraUser) authentication.getUserAuthentication().getPrincipal();
			final Map<String, Object> additionalInfo = new HashMap<>(4);

			additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PROJECT_LICENSE);
			additionalInfo.put(SecurityConstants.DETAILS_USER_ID, sakuraUser.getId());
			additionalInfo.put(SecurityConstants.DETAILS_USERNAME, sakuraUser.getUsername());
			additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, sakuraUser.getDeptId());

			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}

}
