package com.sakura.user.detail.feign;

import com.sakura.common.core.util.R;
import com.sakura.common.core.constant.SecurityConstants;

import com.sakura.common.core.constant.ServiceNameConstants;
import com.sakura.user.detail.feign.factory.RemoteTokenServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Mr.Zhou
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE,
		fallbackFactory = RemoteTokenServiceFallbackFactory.class)
public interface RemoteTokenService {

	/**
	 * 分页查询token 信息
	 * @param params 分页参数
	 * @param from 内部调用标志
	 * @return page
	 */
	@PostMapping("/token/page")
	R getTokenPage(@RequestBody Map<String, Object> params, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 删除token
	 * @param token token
	 * @param from 调用标志
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	R<Boolean> removeToken(@PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);

}
