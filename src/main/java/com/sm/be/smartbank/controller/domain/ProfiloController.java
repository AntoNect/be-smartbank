package com.sm.be.smartbank.controller.domain;

import org.springframework.http.ResponseEntity;

import com.sm.be.smartbank.model.dto.profilo.getprofilo.GetProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Profilo")
public interface ProfiloController {

	@Operation(summary = "Ritorna le informazioni del profilo loggato")
	public ResponseEntity<GetProfiloResponse> getProfilo();

	@Operation(summary = "Crea un nuovo profilo")
	public ResponseEntity<SetInsertProfiloResponse> setInsertProfilo(@Valid SetInsertProfiloRequest request);

	@Operation(summary = "Aggiorna le informazioni del profilo")
	public ResponseEntity<SetUpdateProfiloResponse> setUpdateProfilo(@Valid SetUpdateProfiloRequest request);

}
