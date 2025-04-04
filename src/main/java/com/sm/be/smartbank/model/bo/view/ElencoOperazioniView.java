package com.sm.be.smartbank.model.bo.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ElencoOperazioniView {
	
	Long getIdOperazione();

	BigDecimal getImporto();

	LocalDateTime getData();

	String getDescrizione();

	Integer getCodTipo();

}
