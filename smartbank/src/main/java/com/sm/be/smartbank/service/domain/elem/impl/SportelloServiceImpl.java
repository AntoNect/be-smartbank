package com.sm.be.smartbank.service.domain.elem.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.model.entity.atm.Atm;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Sportello;
import com.sm.be.smartbank.repository.domain.SportelloRepository;
import com.sm.be.smartbank.service.domain.elem.SportelloService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SportelloServiceImpl implements SportelloService {

	private final SportelloRepository sportelloRepository;

	@Override
	public Sportello setInsertSportello(Operazione operazione, Atm atm) {

		Sportello sportello = new Sportello();
		sportello.setOperazione(operazione);
		sportello.setAtm(atm);
		return sportelloRepository.save(sportello);
	}

	@Override
	public BigDecimal getSumImportoPrelieviMeseCorrente(Long idConto, String mese, String anno) {

		return sportelloRepository.sumImportoPrelieviMeseCorrente(idConto, mese, anno);
	}

	@Override
	public Optional<Sportello> getById(Long idOperazione) {
		return sportelloRepository.findById(idOperazione);
	}

}
