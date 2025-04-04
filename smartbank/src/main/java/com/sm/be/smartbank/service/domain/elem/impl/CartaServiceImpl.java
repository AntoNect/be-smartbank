package com.sm.be.smartbank.service.domain.elem.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.model.bo.CartaBO;
import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.repository.domain.CartaRepository;
import com.sm.be.smartbank.service.domain.elem.CartaService;
import com.sm.be.smartbank.service.domain.elem.ContoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartaServiceImpl implements CartaService {

	private final ContoService contoService;
	private final CartaRepository cartaRepository;

	@Override
	public Carta setAssociaCarta(Conto conto) {

		Slice<Carta> carteDisponibili = cartaRepository
				.findByIdContoIsNullAndDataInizioIsNull(PageRequest.of(0, 1, Sort.by("dataScadenza")));
		if (carteDisponibili.isEmpty() || carteDisponibili.get().findFirst().isEmpty())
			throw new ChkException("{esistenza-carte-disponibili}");

		Carta carta = carteDisponibili.get().findFirst().get();
		carta.setConto(conto);
		carta.setDataInizio(LocalDateTime.now());

		return cartaRepository.save(carta);
	}

	@Override
	public Optional<Carta> getByNumeroAndMeseAndAnnoAndCvv(String numero, String mese, String anno, String cvv) {
		return this.cartaRepository.findByNumeroAndMeseAndAnnoAndCvv(numero, mese, anno, cvv);
	}

	@Override
	public CartaBO getCartaAttiva() {

		Optional<Conto> conto = contoService.getContoAttivo();
		if (conto.isEmpty())
			throw new ChkException("Conto non attivo");

		Optional<Carta> optionalCarta = cartaRepository
				.findByIdContoAndDataInizioIsNotNullAndDataFineIsNull(conto.get().getIdConto());
		if (optionalCarta.isEmpty())
			throw new ChkException("Nessuna carta attiva associata");

		Carta carta = optionalCarta.get();
		String scadenza = (carta.getDataScadenza().getMonthValue() < 10 ? "0" : "")
				+ carta.getDataScadenza().getMonthValue() + "/" + carta.getDataScadenza().getYear();
		return CartaBO.builder().numero(carta.getNumero()).scadenza(scadenza).cvv(carta.getCvv()).build();
	}

}
