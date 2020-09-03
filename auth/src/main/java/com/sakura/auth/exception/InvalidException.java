

package com.sakura.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sakura.auth.component.SakuraAuth2ExceptionSerializer;

/**
 * @author Mr.zhou
 * @date 2019/2/1
 */
@JsonSerialize(using = SakuraAuth2ExceptionSerializer.class)
public class InvalidException extends SakuraAuth2Exception {

	public InvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 426;
	}

}
