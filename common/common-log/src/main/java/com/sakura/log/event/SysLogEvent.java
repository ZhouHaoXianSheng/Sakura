package com.sakura.log.event;

import com.sakura.user.detail.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author Mr.zhou 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
