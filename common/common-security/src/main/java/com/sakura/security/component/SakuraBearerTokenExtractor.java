package com.sakura.security.component;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 改造 {@link BearerTokenExtractor} 对公开权限的请求不进行校验
 * @author Mr.Zhou
 */
@Component
@RequiredArgsConstructor
public class SakuraBearerTokenExtractor extends BearerTokenExtractor {

	private final PathMatcher pathMatcher = new AntPathMatcher();

	private final PermitAllUrlProperties urlProperties;

	/**
	 * 在过滤链上使用，如果token==null就不需要校验权限
	 * 此处只要把公开权限的请求设置为null就可以不进行校验
	 * @param request
	 * @return
	 */
	@Override
	public Authentication extract(HttpServletRequest request) {
		boolean match = urlProperties.getUrls().stream()
				.anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

		return match ? null : super.extract(request);
	}

}
