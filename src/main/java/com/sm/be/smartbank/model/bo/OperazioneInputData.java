package com.sm.be.smartbank.model.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sm.be.smartbank.enumeration.TipoOperazioneEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperazioneInputData {

	private TipoOperazioneEnum tipoOperazione;

	private BigDecimal importo;

	private LocalDateTime data;

}
