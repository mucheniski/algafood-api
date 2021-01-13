package com.algaworks.algafood.util;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJasonSerializer extends JsonSerializer<Page<?>> {

	/*
	 * Esse método permite personalizar o retorno de Page	
	 */
	@Override
	public void serialize(Page<?> page, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeObjectField("content", page.getContent());
		jsonGenerator.writeNumberField("size", page.getSize());
		jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
		jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
		jsonGenerator.writeNumberField("number", page.getNumber());
		jsonGenerator.writeEndObject();
	}

}
