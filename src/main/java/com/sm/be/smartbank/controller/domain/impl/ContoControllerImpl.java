package com.sm.be.smartbank.controller.domain.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.be.smartbank.controller.domain.ContoController;
import com.sm.be.smartbank.mapper.bo.carta.CartaMapper;
import com.sm.be.smartbank.mapper.bo.conto.ContoMapper;
import com.sm.be.smartbank.mapper.bo.profilo.ProfiloMapper;
import com.sm.be.smartbank.model.bo.CartaBO;
import com.sm.be.smartbank.model.bo.CoordinateBancarieBO;
import com.sm.be.smartbank.model.bo.DashBoardBO;
import com.sm.be.smartbank.model.dto.profilo.getcartaattiva.GetCartaAttivaResponse;
import com.sm.be.smartbank.model.dto.profilo.getcoordinatebancarie.GetCoordinateBancarieResponse;
import com.sm.be.smartbank.model.dto.profilo.getdashboard.GetDashboardResponse;
import com.sm.be.smartbank.service.domain.elem.CartaService;
import com.sm.be.smartbank.service.domain.elem.ContoService;

import lombok.AllArgsConstructor;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = { "/api/conto" })
public class ContoControllerImpl implements ContoController {

	private final CartaService cartaService;
	private final ContoService contoService;

	@Override
	@GetMapping("/dashboard")
	public ResponseEntity<GetDashboardResponse> getDashboard() {

		ProfiloMapper mapper = Mappers.getMapper(ProfiloMapper.class);
		DashBoardBO bo = contoService.getDashboard();
		return ResponseEntity.ok(mapper.toGetDashboardResponse(bo));
	}

	@Override
	@GetMapping("/carta-attiva")
	public ResponseEntity<GetCartaAttivaResponse> getCartaAttiva() {

		CartaMapper mapper = Mappers.getMapper(CartaMapper.class);
		CartaBO carta = cartaService.getCartaAttiva();
		return ResponseEntity.ok(mapper.toGetCartaAttivaResponse(carta));
	}

	@Override
	@GetMapping("/coordinate-bancarie")
	public ResponseEntity<GetCoordinateBancarieResponse> getCoordinateBancarie() {

		ContoMapper mapper = Mappers.getMapper(ContoMapper.class);
		CoordinateBancarieBO conto = contoService.getCoordinateBancarie();
		return ResponseEntity.ok(mapper.toGetCoordinateBancarieResponse(conto));
	}

}
