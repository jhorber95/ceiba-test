package com.ceiba.demo.service.mapper;

import com.ceiba.demo.model.Pagos;
import com.ceiba.demo.service.dto.PagoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagoMapper extends EntityMapper<PagoDto, Pagos> {
}
