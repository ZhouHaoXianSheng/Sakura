package com.sakura.user.detail.feign;

import com.sakura.common.core.util.R;
import com.sakura.common.core.constant.SecurityConstants;
import com.sakura.common.core.constant.ServiceNameConstants;
import com.sakura.user.detail.entity.SysLog;
import com.sakura.user.detail.feign.factory.RemoteLogServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Mr.Zhou
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @param from 内部调用标志
	 * @return succes、false
	 */
	@PostMapping("/log")
	R<Boolean> saveLog(@RequestBody SysLog sysLog, @RequestHeader(SecurityConstants.FROM) String from);

}
