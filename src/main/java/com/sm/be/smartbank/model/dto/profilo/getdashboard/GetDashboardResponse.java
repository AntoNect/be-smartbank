package com.sm.be.smartbank.model.dto.profilo.getdashboard;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetDashboardResponse {

	private BigDecimal saldo;

	private BigDecimal totEntrateUltimoMese;

	private BigDecimal totUsciteUltimeMese;

	private String dataInizioUltimoMese;

	private String dataFineUltimoMese;

}
