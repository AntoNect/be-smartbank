package com.sm.be.smartbank.model.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepositoInputData {

	private String numeroCarta;

	private String meseScadenzaCarta;

	private String annoScadenzaCarta;

	private String cvvCarta;

	private int importo;

	private Long idAtm;

}
