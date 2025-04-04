package com.sm.be.smartbank.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BonificoDTO {

	private String iban;

	private String nome;

	private String causale;

	private Boolean istantaneo;

	private String dataAccredito;

	private String dataAddebito;

	private int codStato;
	
	private String descStato;

}
