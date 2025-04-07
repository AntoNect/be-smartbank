package com.sm.be.smartbank.service.domain.elem.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.enumeration.StatoBonificoEnum;
import com.sm.be.smartbank.model.bo.BonificoIngressoInputData;
import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.repository.domain.BonificoRepository;
import com.sm.be.smartbank.service.domain.chk.OperazioneChk;
import com.sm.be.smartbank.service.domain.elem.BonificoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BonificoServiceImpl implements BonificoService {

	private BonificoRepository bonificoRepository;

	@Override
	public Bonifico setInsertBonificoInUscita(Operazione operazione, BonificoUscitaInputData inputData) {

		// effettuo i controlli preliminari sui dai in input
		OperazioneChk.chkSetInsertBonifico(inputData);

		Bonifico bonifico = new Bonifico();
		bonifico.setOperazione(operazione);
		bonifico.setIban(inputData.getIbanBeneficiario());
		bonifico.setNome(inputData.getNomeBeneficiario());
		bonifico.setCausale(inputData.getCausale());
		bonifico.setIstantaneo(inputData.isIstantaneo());

		LocalDateTime localDateTime = LocalDateTime.now();
		if (Boolean.TRUE.equals(bonifico.getIstantaneo())) {

			// bonifico istantaneo
			bonifico.setDataAddebito(localDateTime);
			bonifico.setDataAccredito(localDateTime);
			bonifico.setCodStato(StatoBonificoEnum.CONTABILIZZATO.getValue());
		} else if (inputData.getDataAddebito() != null && inputData.getDataAddebito().isAfter(LocalDate.now())) {

			// bonifico prenotato in data futura
			bonifico.setDataAddebito(inputData.getDataAddebito().atTime(11, 0, 0));

			if (inputData.getDataAddebito().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				// la data addebito è di venerdì -> imposto come data accredito il lunedì della
				// settimana successiva
				bonifico.setDataAccredito(
						inputData.getDataAddebito().atTime(11, 0, 0).with(DayOfWeek.MONDAY).plusWeeks(1));
			} else {
				// la data addebito NON è di venerdì -> imposto come data accredito il giorno
				// successivo
				bonifico.setDataAccredito(inputData.getDataAddebito().atTime(11, 0, 0).plusDays(1));
			}
			bonifico.setCodStato(StatoBonificoEnum.PRENOTATO.getValue());
		} else {

			// bonifico NON istantaneo -> applico la policy dei bonifici NON istantanei
			if (localDateTime.getHour() >= 16) {

				// orario corrente dopo le 16: NON è possibile effettuare l'addebito nella
				// giornata odierna. Devo procere con il calcolo della prima prossima data
				// disponibile
				if (localDateTime.getDayOfWeek().getValue() >= DayOfWeek.THURSDAY.getValue()) {
				/** ..continua **/		
					// è giovedì, venerdì, sabato o domenica dopo le 16 -> la prima data disponibile
					// è il lunedì seguente alle ore 11
					LocalDateTime prossimoLunedi = localDateTime.with(DayOfWeek.MONDAY).plusWeeks(1).withHour(11)
							.withMinute(0).withSecond(0).withNano(0);
					bonifico.setDataAddebito(prossimoLunedi);
					bonifico.setDataAccredito(prossimoLunedi.plusDays(1));
				} else {

					// è lunedì, martedì o mercoledì dopo le 16 -> la prima data disponibile è il
					// giorno seguente alle ore 11
					bonifico.setDataAddebito(
							localDateTime.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0));
					bonifico.setDataAccredito(bonifico.getDataAddebito().plusDays(1));

				}
				bonifico.setCodStato(StatoBonificoEnum.PRENOTATO.getValue());
			} else {

				// orario corrente prima delle 16: è possibile effettuare l'addebito nella
				// giornata ordiera ad esclusione dei giorni venerdì, sabato o domenica
				if (localDateTime.getDayOfWeek().getValue() >= DayOfWeek.FRIDAY.getValue()) {

					// è venerdì, sabato o domenica prima delle 16 -> la prima data disponibile è il
					// lunedì seguente alle ore 11
					LocalDateTime prossimoLunedi = localDateTime.with(DayOfWeek.MONDAY).plusWeeks(1).withHour(11)
							.withMinute(0).withSecond(0).withNano(0);
					bonifico.setDataAddebito(prossimoLunedi);
					bonifico.setDataAccredito(prossimoLunedi.plusDays(1));
				} else {

					// è lunedì, martedì, mercoledì o giovedì prima delle 16 -> posso fare
					// l'addebito nella giornata seguente e l'accredito nella giornata successiva
					bonifico.setDataAddebito(localDateTime);
					bonifico.setDataAccredito(
							localDateTime.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0));
				}
				bonifico.setCodStato(StatoBonificoEnum.IN_ATTESA.getValue());
			}
		}

		return bonificoRepository.save(bonifico);
	}

	@Override
	public Bonifico setInsertBonificoInIngresso(Operazione operazione, BonificoIngressoInputData inputData) {

		Bonifico bonifico = new Bonifico();
		bonifico.setOperazione(operazione);
		bonifico.setNome(inputData.getNomeMittente());
		bonifico.setCausale(inputData.getCausale());
		bonifico.setIstantaneo(inputData.isIstantaneo());
		bonifico.setDataAccredito(inputData.getDataAccredito());
		bonifico.setCodStato(StatoBonificoEnum.CONTABILIZZATO.getValue());

		return bonificoRepository.save(bonifico);
	}

	@Override
	public Optional<Bonifico> getById(Long idOperazione) {
		return bonificoRepository.findById(idOperazione);
	}

}
