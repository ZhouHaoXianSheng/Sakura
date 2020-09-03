package com.sakura.auth.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sakura.auth.exception.SakuraAuth2Exception;

import com.sakura.common.core.constant.CommonConstants;
import lombok.SneakyThrows;

/**
 * @author Mr.zhou
 * OAuth2 异常格式化
 */
public class SakuraAuth2ExceptionSerializer extends StdSerializer<SakuraAuth2Exception> {

	public SakuraAuth2ExceptionSerializer() {
		super(SakuraAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(SakuraAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}

}
