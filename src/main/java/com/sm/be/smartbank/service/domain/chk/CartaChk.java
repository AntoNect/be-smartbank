package com.sm.be.smartbank.service.domain.chk;

import java.time.LocalDateTime;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.model.entity.carta.Carta;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartaChk {

	public static void chkCartaAttiva(Carta carta) {

		if (carta.getDataInizio() == null || (carta.getDataInizio() != null && carta.getDataFine() != null
				&& carta.getDataFine().isBefore(LocalDateTime.now())))
			throw new ChkException("Carta non attiva");

	}

}
