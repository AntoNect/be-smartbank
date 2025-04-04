package com.sm.be.smartbank.service.domain.aggr.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.be.smartbank.config.security.AuthUtil;
import com.sm.be.smartbank.model.bo.ProfiloInputData;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.model.entity.personale.Utente;
import com.sm.be.smartbank.service.domain.aggr.AggrProfiloService;
import com.sm.be.smartbank.service.domain.elem.CartaService;
import com.sm.be.smartbank.service.domain.elem.ContoService;
import com.sm.be.smartbank.service.domain.elem.ProfiloService;
import com.sm.be.smartbank.service.domain.elem.UtenteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AggrProfiloServiceImpl implements AggrProfiloService {

	private final AuthUtil authUtil;
	private final ContoService contoService;
	private final CartaService cartaService;
	private final UtenteService utenteService;
	private final ProfiloService profiloService;

	@Override
	@Transactional
	public Profilo setInsertProfilo(ProfiloInputData inputData) {

		Utente utente = utenteService.getByUsername(authUtil.getUsername());

		Profilo profilo = profiloService.setInsertProfilo(utente, inputData);

		Conto conto = contoService.setInsertConto(profilo);

		cartaService.setAssociaCarta(conto);

		return profilo;
	}

}
