package com.sm.be.smartbank.mapper.bo.conto;

import org.mapstruct.Mapper;

import com.sm.be.smartbank.model.bo.CoordinateBancarieBO;
import com.sm.be.smartbank.model.dto.profilo.getcoordinatebancarie.GetCoordinateBancarieResponse;

@Mapper
public interface ContoMapper {

	GetCoordinateBancarieResponse toGetCoordinateBancarieResponse(CoordinateBancarieBO conto);

}
