package com.ceiba.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@Entity(name = "pagos")
@Table(name = "pagos")
@AllArgsConstructor
@NoArgsConstructor
public class Pagos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "documento_identificacion_arrendatario", nullable = false)
	private int documentoIdentificacionArrendatario;
	
	@Column(name = "codigo_inmueble", nullable = false)
	private String codigoInmueble;
	
	@Column(name = "valor_pagado", nullable = false)
	private int valorPagado;
	
	@Column(name = "fecha_pago", nullable = false)
	private LocalDate fechaPago;
}
