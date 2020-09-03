package com.sakura.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Zhou
 * 放行参数设置，用于验证码过滤器
 * @ConfigurationProperties(prefix = "ignore")：
 * 捕获配置文件前缀为ignore的参数并且自动注入给元素
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
public class IgnoreClientConfiguration {

	/**
	 * 放行终端配置，网关不校验此处的终端
	 */
	private List<String> clients = new ArrayList<>();

}
