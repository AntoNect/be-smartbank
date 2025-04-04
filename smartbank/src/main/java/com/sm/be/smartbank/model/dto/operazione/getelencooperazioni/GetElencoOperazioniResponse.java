package com.sm.be.smartbank.model.dto.operazione.getelencooperazioni;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetElencoOperazioniResponse {
	
	private Long idOperazione;

	private BigDecimal importo;

	private String data;

	private String descrizione;

	private Integer codTipo;

}
