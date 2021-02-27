package com.ceiba.demo.api;

import com.ceiba.demo.model.Pagos;
import com.ceiba.demo.response.PagosResponse;
import com.ceiba.demo.service.PagosService;
import com.ceiba.demo.service.dto.PagoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pagos")
public class PagosRest {
	
	private final PagosService service;


	@PostMapping("")
	public PagosResponse registrar(@RequestBody @Valid PagoDto entidad) {
		return service.registrar(entidad);
	}
	
	@GetMapping("")
	public List<Pagos> listarTodos() {
		return service.listarTodos();
	}
	
}
