package com.sakura.security.annotation.component;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Mr.zhou
 * 此处应该是自动扫描包，加上@ComponentScan就不需要
 * ConfigurationPropertiesScan
 * ComponentScan("com.sakura.security")
 */
@ConfigurationPropertiesScan
@ComponentScan("com.sakura.security")
public class SakuraResourceServerAutoConfiguration {

	/**
	 * 原先spring容器已经有一个RestTemplate，所以此处需要加上@Primary
	 * @return
	 */
	@Bean
	@Primary
	//@LoadBalanced
	public RestTemplate lbRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		// 传递ACCEPT JSON
		restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			return execution.execute(request, body);
		}));

		// 忽略400 异常
		// 如果不忽略就会造成接受到400错误导致restTemplate错误，但是页面却是500错误，500错误范围太广不能处理
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			@SneakyThrows
			public void handleError(ClientHttpResponse response) {
				if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
					super.handleError(response);
				}
			}
		});

		return restTemplate;
	}

}
