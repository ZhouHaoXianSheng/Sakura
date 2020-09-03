package com.sakura.user.detail.feign.fallback;

import com.sakura.common.core.util.R;
import com.sakura.user.detail.entity.SysLog;
import com.sakura.user.detail.feign.RemoteLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Zhou
 */
@Slf4j
@Component
public class RemoteLogServiceFallbackImpl implements RemoteLogService {

	@Setter
	private Throwable cause;

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @param from 内部调用标志
	 * @return succes、false
	 */
	@Override
	public R<Boolean> saveLog(SysLog sysLog, String from) {
		log.error("feign 插入日志失败", cause);
		return null;
	}

}
