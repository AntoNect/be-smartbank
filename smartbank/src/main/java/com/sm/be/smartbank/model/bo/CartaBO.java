package com.sm.be.smartbank.model.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartaBO {

	private String numero;
	
	private String scadenza;
	
	private String cvv;
	
}
