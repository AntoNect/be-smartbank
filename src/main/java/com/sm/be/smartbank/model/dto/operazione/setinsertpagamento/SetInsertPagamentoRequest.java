package com.sm.be.smartbank.model.dto.operazione.setinsertpagamento;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetInsertPagamentoRequest {

	@NotBlank(message = "Il numero della carta è obbligatorio")
	@Pattern(regexp = "\\d{16}", message = "Il numero della carta deve essere di 16 cifre")
	private String numeroCarta;

	@NotBlank(message = "Il mese di scadenza è obbligatorio")
	@Pattern(regexp = "0[1-9]|1[0-2]", message = "Il mese di scadenza deve essere compreso tra 01 e 12")
	private String meseScadenzaCarta;

	@NotBlank(message = "L'anno di scadenza è obbligatorio")
	@Pattern(regexp = "\\d{2}", message = "L'anno di scadenza deve essere di 2 cifre")
	private String annoScadenzaCarta;

	@NotBlank(message = "Il CVV è obbligatorio")
	@Pattern(regexp = "\\d{3}", message = "Il CVV deve essere di 3 cifre")
	private String cvvCarta;

	@NotNull(message = "L'importo è obbligatorio")
	@DecimalMin(value = "0.01", message = "L'importo deve essere maggiore di zero")
	private BigDecimal importo;

	@NotBlank(message = "L'esercente è obbligatorio")
	private String esercente;

}
