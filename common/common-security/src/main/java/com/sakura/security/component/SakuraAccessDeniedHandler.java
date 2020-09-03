package com.sakura.security.component;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.common.core.util.R;
import com.sakura.common.core.constant.CommonConstants;
import com.sakura.security.exception.SakuraDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Mr.zhou
 * 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler包装失败信息到SakuraDeniedException
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SakuraAccessDeniedHandler extends OAuth2AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	/**
	 * 授权拒绝处理，使用R包装
	 * @param request request
	 * @param response response
	 * @param authException authException
	 */
	@Override
	@SneakyThrows
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
		log.info("授权失败，禁止访问 {}", request.getRequestURI());

		response.setCharacterEncoding(CommonConstants.UTF8);
		response.setContentType(CommonConstants.CONTENT_TYPE);

		R<SakuraDeniedException> result = R.failed(new SakuraDeniedException("授权失败，禁止访问"));
		response.setStatus(HttpStatus.HTTP_FORBIDDEN);
		PrintWriter printWriter = response.getWriter();
		printWriter.append(objectMapper.writeValueAsString(result));
	}

}
