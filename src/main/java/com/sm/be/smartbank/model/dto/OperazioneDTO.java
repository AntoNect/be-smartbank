package com.sm.be.smartbank.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperazioneDTO {

	private Long idOperazione;
	private BigDecimal importo;
	private String data;
	private int codTipoOperazione;
	private String descTipoOperazione;

	private BonificoDTO bonifico;
	private PagamentoDTO pagamento;
	private SportelloDTO sportello;

}
