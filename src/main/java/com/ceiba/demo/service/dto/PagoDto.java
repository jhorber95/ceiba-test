package com.ceiba.demo.service.dto;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDto {

    private Integer id;

    @NotNull
    private Integer documentoIdentificacionArrendatario;
    @NotNull
    private String codigoInmueble;
    @NotNull
    @Max(value = 1000000)
    private Integer valorPagado;
    @NotNull
    private LocalDate fechaPago;

}
