package com.sakura.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sakura.auth.component.SakuraAuth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author Mr.zhou
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = SakuraAuth2ExceptionSerializer.class)
public class SakuraAuth2Exception extends OAuth2Exception {

	@Getter
	private String errorCode;

	public SakuraAuth2Exception(String msg) {
		super(msg);
	}

	public SakuraAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

}
