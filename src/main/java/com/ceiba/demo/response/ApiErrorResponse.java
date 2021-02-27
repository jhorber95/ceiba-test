package com.ceiba.demo.response;

import org.springframework.http.HttpStatus;

// @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error en validacion")
@SuppressWarnings("serial")
public class ApiErrorResponse extends RuntimeException {
	
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	
	public HttpStatus getHttpStatus() {
        return httpStatus;
    }

	public ApiErrorResponse(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
