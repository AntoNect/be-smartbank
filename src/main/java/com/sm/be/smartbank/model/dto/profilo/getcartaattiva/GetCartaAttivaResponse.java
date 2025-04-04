package com.sm.be.smartbank.model.dto.profilo.getcartaattiva;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetCartaAttivaResponse {
	
	private String numero;
	
	private String scadenza;
	
	private String cvv;

}
