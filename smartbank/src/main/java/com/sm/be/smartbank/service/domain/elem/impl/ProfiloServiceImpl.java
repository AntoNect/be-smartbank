package com.sm.be.smartbank.service.domain.elem.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.config.security.AuthUtil;
import com.sm.be.smartbank.model.bo.ProfiloInputData;
import com.sm.be.smartbank.model.entity.localita.Citta;
import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.model.entity.personale.Utente;
import com.sm.be.smartbank.repository.domain.ProfiloRepository;
import com.sm.be.smartbank.service.domain.chk.ProfiloChk;
import com.sm.be.smartbank.service.domain.elem.CittaService;
import com.sm.be.smartbank.service.domain.elem.ProfiloService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfiloServiceImpl implements ProfiloService {

	private final AuthUtil authUtil;
	private final CittaService cittaService;
	private final ProfiloRepository profiloRepository;

	@Override
	public Profilo getProfilo() {
		String userName = authUtil.getUsername();
		return profiloRepository.findByUsername(userName);
	}

	@Override
	public Profilo setUpdateProfilo(ProfiloInputData inputData) {

		String userName = authUtil.getUsername();
		Profilo profilo = profiloRepository.findByUsername(userName);

		if (profilo == null)
			throw new ChkException("{esistenza-profilo-aggiornamento}");

		// verifico la validità del numero di telefono
		ProfiloChk.chkTelefono(inputData.getTelefono());

		// verifico se il numero di telefono non è già associato ad un altro utente
		if (profiloRepository.existsByTelefonoAndIdProfiloNot(inputData.getTelefono(), profilo.getIdProfilo()))
			throw new ChkException("{esistenza-telefono}");

		// verifico la correttezza del cap
		Optional<Citta> optionalCitta = cittaService.getById(inputData.getIdCittaResidenza());
		if (optionalCitta.isEmpty())
			throw new ChkException("{validita-citta}");

		Citta citta = optionalCitta.get();

		profilo.setTelefono(inputData.getTelefono());
		profilo.setIndirizzo(inputData.getIndirizzoResidenza());
		profilo.setCittaResidenza(citta);
		profilo.setDataNascita(inputData.getDataNascita());

		return profiloRepository.save(profilo);
	}

	@Override
	public Profilo setInsertProfilo(Utente utente, ProfiloInputData inputData) {

		// verifico se già esiste un profilo per l'utente loggato
		if (profiloRepository.existsByIdUtente(utente.getIdUtente()))
			throw new ChkException("{esistenza-profilo-inserimento}");

		// verifico la validità del codice fiscale
		ProfiloChk.chkCodiceFiscale(inputData.getCodiceFiscale());

		// verifico se il codice fiscale è già registrato
		if (profiloRepository.existsByCodiceFiscale(inputData.getCodiceFiscale()))
			throw new ChkException("{esistenza-codice-fiscale}");

		// verifico la validità del numero di telefono
		ProfiloChk.chkTelefono(inputData.getTelefono());

		// verifico se il numero di telefono non è già associato ad un altro utente
		if (profiloRepository.existsByTelefono(inputData.getTelefono()))
			throw new ChkException("{esistenza-telefono}");

		// verifico la correttezza della citta
		Optional<Citta> optionalCitta = cittaService.getById(inputData.getIdCittaResidenza());
		if (optionalCitta.isEmpty())
			throw new ChkException("{validita-citta}");
		Citta citta = optionalCitta.get();

		Profilo profilo = new Profilo();
		profilo.setUtente(utente);
		profilo.setNome(inputData.getNome());
		profilo.setCognome(inputData.getCognome());
		profilo.setCodiceFiscale(inputData.getCodiceFiscale());
		profilo.setTelefono(inputData.getTelefono());
		profilo.setIndirizzo(inputData.getIndirizzoResidenza());
		profilo.setCittaResidenza(citta);
		profilo.setDataNascita(inputData.getDataNascita());

		return profiloRepository.save(profilo);
	}

}
