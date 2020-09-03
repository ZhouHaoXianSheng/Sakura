package com.sakura.security.exception;

import lombok.NoArgsConstructor;

/**
 * @author Mr.zhou
 * @date 2018年06月22日16:22:03 403 授权拒绝
 */
@NoArgsConstructor
public class SakuraDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SakuraDeniedException(String message) {
		super(message);
	}

	public SakuraDeniedException(Throwable cause) {
		super(cause);
	}

	public SakuraDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SakuraDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
