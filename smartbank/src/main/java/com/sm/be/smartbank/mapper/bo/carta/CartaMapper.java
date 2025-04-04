package com.sm.be.smartbank.mapper.bo.carta;

import org.mapstruct.Mapper;

import com.sm.be.smartbank.model.bo.CartaBO;
import com.sm.be.smartbank.model.dto.profilo.getcartaattiva.GetCartaAttivaResponse;

@Mapper
public interface CartaMapper {

	GetCartaAttivaResponse toGetCartaAttivaResponse(CartaBO cartaBO);

}
