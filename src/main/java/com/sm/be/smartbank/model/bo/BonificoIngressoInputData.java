package com.sm.be.smartbank.model.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BonificoIngressoInputData {

	private String nomeMittente;

	private BigDecimal importo;

	private String causale;

	private boolean istantaneo;

	private LocalDateTime dataAccredito;

}
