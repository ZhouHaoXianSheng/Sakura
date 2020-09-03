

package com.sakura.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sakura.auth.component.SakuraAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author Mr.zhou
 * @date 2019/2/1
 */
@JsonSerialize(using = SakuraAuth2ExceptionSerializer.class)
public class MethodNotAllowed extends SakuraAuth2Exception {

	public MethodNotAllowed(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "method_not_allowed";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.METHOD_NOT_ALLOWED.value();
	}

}
