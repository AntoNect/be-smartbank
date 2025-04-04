package com.sm.be.smartbank.service.domain.chk;

import java.time.LocalDateTime;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.model.entity.personale.Conto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContoChk {

	public static void chkContoAttivo(Conto conto) {

		if (conto.getDataChiusura() != null && conto.getDataChiusura().isBefore(LocalDateTime.now()))
			throw new ChkException("Conto non attivo");

	}

}
