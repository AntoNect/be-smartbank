package com.sm.be.smartbank.service.domain.elem;

import java.util.Optional;

import com.sm.be.smartbank.model.bo.CartaBO;
import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.model.entity.personale.Conto;

public interface CartaService {

	Carta setAssociaCarta(Conto conto);

	Optional<Carta> getByNumeroAndMeseAndAnnoAndCvv(String numero, String mese, String anno, String cvv);
	
	CartaBO getCartaAttiva();

}
