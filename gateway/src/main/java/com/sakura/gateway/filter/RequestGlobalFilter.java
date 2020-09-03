package com.sakura.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author zhouhao
 * <p>
 * 全局拦截器，作用所有的微服务。
 * 由于原先的StripPrefixGatewayFilterFactory，removeHeader等作用于单个路由
 * 此处重写为全局过滤器
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,（跳过的参数部分）支持全局
 * <p>
 */
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * Process the Web request and (optionally) delegate to the next {@code WebFilter}
	 * through the given {@link GatewayFilterChain}.
	 * @param exchange the current server exchange
	 * @param chain provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//此处方法用于处理参数等问题
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove("from")).build();

		//此处方法用于处理parm和路径相关的问题
		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String path = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(path, "/")).skip(1L)
				.collect(Collectors.joining("/"));
		newPath += (newPath.length() > 1 && path.endsWith("/") ? "/" : "");
		ServerHttpRequest newRequest = request.mutate().path(newPath).build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	/**
	 * 过滤器的优先顺序，越小优先越高，越早执行
	 * @return
	 */
	@Override
	public int getOrder() {
		return -1000;
	}

}
