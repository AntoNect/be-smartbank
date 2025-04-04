package com.sm.be.smartbank.service.domain.chk;

import com.sm.be.smartbank.config.exception.custom.ChkException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfiloChk {

	public static void chkCodiceFiscale(String codiceFiscale) {
		String regex = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
		if (Boolean.FALSE.equals(codiceFiscale.matches(regex)))
			throw new ChkException("{validita-codice-fiscale}");
	}

	public static void chkTelefono(String telefono) {
		String regex = "^(\\+39)?\\s?3[0-9]{2}\\s?[0-9]{6,7}$";
		if (Boolean.FALSE.equals(telefono.matches(regex)))
			throw new ChkException("{validita-telefono}");
	}

}
