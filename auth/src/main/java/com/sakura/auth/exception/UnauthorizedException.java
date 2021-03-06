

package com.sakura.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sakura.auth.component.SakuraAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author Mr.zhou
 * @date 2019/2/1
 */
@JsonSerialize(using = SakuraAuth2ExceptionSerializer.class)
public class UnauthorizedException extends SakuraAuth2Exception {

	public UnauthorizedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "unauthorized";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}

}
