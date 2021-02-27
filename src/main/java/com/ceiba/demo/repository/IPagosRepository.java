package com.ceiba.demo.repository;

import com.ceiba.demo.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPagosRepository extends JpaRepository<Pagos, Integer> {

	@Query(value = "SELECT SUM(valor_pagado), documento_identificacion_arrendatario  FROM pagos WHERE documento_identificacion_arrendatario = ?1 GROUP BY documento_identificacion_arrendatario", nativeQuery = true)
	public int abonoCliente(int numeroIdentificacion);
}
