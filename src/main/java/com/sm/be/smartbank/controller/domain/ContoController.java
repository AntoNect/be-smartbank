package com.sm.be.smartbank.controller.domain;

import org.springframework.http.ResponseEntity;

import com.sm.be.smartbank.model.dto.profilo.getcartaattiva.GetCartaAttivaResponse;
import com.sm.be.smartbank.model.dto.profilo.getcoordinatebancarie.GetCoordinateBancarieResponse;
import com.sm.be.smartbank.model.dto.profilo.getdashboard.GetDashboardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conto")
public interface ContoController {

	@Operation(summary = "Ritorna le informazioni da visualizzare nella dashboard")
	public ResponseEntity<GetDashboardResponse> getDashboard();

	@Operation(summary = "Ritorna la carta attiva associata al profilo")
	public ResponseEntity<GetCartaAttivaResponse> getCartaAttiva();

	@Operation(summary = "Ritorna le coordinate bancarie associate al conto attivo")
	public ResponseEntity<GetCoordinateBancarieResponse> getCoordinateBancarie();

}
