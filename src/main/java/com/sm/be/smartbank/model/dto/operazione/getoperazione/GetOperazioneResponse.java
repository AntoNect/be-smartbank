package com.sm.be.smartbank.model.dto.operazione.getoperazione;

import java.math.BigDecimal;

import com.sm.be.smartbank.model.dto.BonificoDTO;
import com.sm.be.smartbank.model.dto.PagamentoDTO;
import com.sm.be.smartbank.model.dto.SportelloDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetOperazioneResponse {

	private Long idOperazione;
	private BigDecimal importo;
	private String data;
	private int codTipoOperazione;
	private String descTipoOperazione;

	private BonificoDTO bonifico;
	private PagamentoDTO pagamento;
	private SportelloDTO sportello;

}
