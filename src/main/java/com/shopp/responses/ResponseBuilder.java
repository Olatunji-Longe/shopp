package com.shopp.responses;

import org.apache.commons.text.WordUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

	private RestResponse response;
	private static final String DATA_KEY = "data";
	private static final String TYPE_KEY = "type";
	private static final String ERROR_KEY = "error";

	public ResponseBuilder(){
		this.response = new RestResponse();
	}

	public ResponseBuilder data(String key, Object value){
		this.response.put(key, value);
		return this;
	}

	public ResponseBuilder data(Object data){
		String key = data != null ? WordUtils.uncapitalize(data.getClass().getSimpleName()) : DATA_KEY;
		this.response.put(key, data);
		return this;
	}

	public ResponseBuilder type(String type){
		this.response.put(TYPE_KEY, type);
		return this;
	}

	private ResponseBuilder error(String message){
		this.response.put(ERROR_KEY, message);
		return this;
	}

	public ResponseEntity<RestResponse> httpError(HttpStatus httpStatus, String message){
		return ResponseEntity.status(httpStatus).body(
				this.error(message).build()
		);
	}

	public RestResponse build(){
		return this.response;
	}

}
