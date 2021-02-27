package com.ceiba.demo.service;

import com.ceiba.demo.model.Pagos;
import com.ceiba.demo.repository.IPagosRepository;
import com.ceiba.demo.response.PagosResponse;
import com.ceiba.demo.service.dto.PagoDto;
import com.ceiba.demo.service.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagosService {
	
	private int valorArriendo = 1000000;
	
	private final IPagosRepository repo;
	private final PagoMapper mapper;

	public List<Pagos> listarTodos() {
		return repo.findAll();
	}

	public PagosResponse registrar(PagoDto dto) {

		Pagos entidad = mapper.toEntity(dto);
		
		try {
			
			/* validar que el valor pagado no supere el millon */
			// if (entidad.getValorPagado() > this.valorArriendo) {
			if (entidad.getValorPagado() > this.valorArriendo)  {
				throw new Exception("el valor pagado no puede ser mayor a 1000000");
			}
			
			LocalDate fechaPago = entidad.getFechaPago();
			
			/* validar que la fecha del pago sea un dia impar */
			if ((fechaPago.getDayOfMonth() % 2) == 0) {
				throw new Exception("lo siento pero se puede recibir el pago por decreto de administracion");
			}
			
			repo.save(entidad);
			String message = "gracias por pagar todo tu arriendo";
			
			if (entidad.getValorPagado() < this.valorArriendo) {
				
				/* obtener los posibles abonos realizados por el arrendatario */
				int totalAbonos = repo.abonoCliente(entidad.getDocumentoIdentificacionArrendatario());
				
				/* */
				if (totalAbonos != this.valorArriendo) {
					int saldo = this.valorArriendo - totalAbonos;
					message = "gracias por tu abono, sin embargo reacuerda que te hace falta pagar " + saldo;
				}
			}
			
			return new PagosResponse(message);
			
		} catch(DateTimeParseException e) {
			return new PagosResponse("La fecha ingresada no es valida");
		} catch(Exception e) {
			return new PagosResponse(e.getMessage());
		}
	}
}
