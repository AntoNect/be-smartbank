package com.sm.be.smartbank.model.bo;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SportelloInputData {

	private String numeroCarta;

	private String meseScadenzaCarta;

	private String annoScadenzaCarta;

	private String cvvCarta;

	private BigDecimal importo;

	private Long idSportello;

}
