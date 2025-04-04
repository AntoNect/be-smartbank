package com.sm.be.smartbank.model.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BonificoUscitaInputData {

	private String nomeBeneficiario;

	private String ibanBeneficiario;

	private BigDecimal importo;

	private String causale;

	private boolean istantaneo;

	private LocalDate dataAddebito;

}
