package com.sm.be.smartbank.controller.domain.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.be.smartbank.controller.domain.ProfiloController;
import com.sm.be.smartbank.mapper.bo.profilo.ProfiloMapper;
import com.sm.be.smartbank.model.dto.profilo.getprofilo.GetProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloResponse;
import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.service.domain.aggr.AggrProfiloService;
import com.sm.be.smartbank.service.domain.elem.ProfiloService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(value = { "/api/profilo" })
public class ProfiloControllerImpl implements ProfiloController {

	private final ProfiloService profiloService;
	private final AggrProfiloService aggrProfiloService;

	@Override
	@GetMapping("")
	public ResponseEntity<GetProfiloResponse> getProfilo() {

		ProfiloMapper mapper = Mappers.getMapper(ProfiloMapper.class);
		Profilo profilo = profiloService.getProfilo();
		return ResponseEntity.ok(mapper.toGetProfiloResponse(profilo));
	}

	@Override
	@PostMapping("/insert")
	public ResponseEntity<SetInsertProfiloResponse> setInsertProfilo(
			@Valid @RequestBody SetInsertProfiloRequest request) {

		ProfiloMapper mapper = Mappers.getMapper(ProfiloMapper.class);
		Profilo view = aggrProfiloService.setInsertProfilo(mapper.toProfiloInputData(request));
		return ResponseEntity.ok(mapper.toSetInsertProfiloResponse(view));
	}

	@Override
	@PutMapping("/update")
	public ResponseEntity<SetUpdateProfiloResponse> setUpdateProfilo(
			@Valid @RequestBody SetUpdateProfiloRequest request) {

		ProfiloMapper mapper = Mappers.getMapper(ProfiloMapper.class);
		Profilo profilo = profiloService.setUpdateProfilo(mapper.toProfiloInputData(request));
		return ResponseEntity.ok(mapper.toSetUpdateProfiloResponse(profilo));
	}

}
