package com.sm.be.smartbank.model.dto.operazione.setinsertbonificoinuscita;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertBonificoInUscitaRequest {

	private String nomeBeneficiario;

	private String ibanBeneficiario;

	private BigDecimal importo;

	private String causale;

	private boolean istantaneo;

	private LocalDate dataAddebito;

}
