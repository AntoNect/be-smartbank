package com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertBonificoInUscitaResponse {

	private String beneficiario;

	private String causale;

	private Boolean istantaneo;

	private BigDecimal importo;

	private LocalDateTime dataAccredito;

	private LocalDateTime dataAddebito;

}
