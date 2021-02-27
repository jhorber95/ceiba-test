package com.ceiba.demo.response;

import lombok.Data;

@Data
public class PagosResponse {
	
	String respuesta;
	
	public PagosResponse(String respuesta) {
		this.respuesta = respuesta;
	}
}
