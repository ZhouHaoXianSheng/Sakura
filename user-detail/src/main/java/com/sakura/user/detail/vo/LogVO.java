package com.sakura.user.detail.vo;

import com.sakura.user.detail.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Zhou
 */
@Data
public class LogVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private SysLog sysLog;

	private String username;

}
