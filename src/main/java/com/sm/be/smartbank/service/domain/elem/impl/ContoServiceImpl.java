package com.sm.be.smartbank.service.domain.elem.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.config.security.AuthUtil;
import com.sm.be.smartbank.model.bo.CoordinateBancarieBO;
import com.sm.be.smartbank.model.bo.DashBoardBO;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.model.entity.personale.Profilo;
import com.sm.be.smartbank.repository.domain.ContoRepository;
import com.sm.be.smartbank.service.domain.elem.ContoService;
import com.sm.be.smartbank.service.domain.elem.OperazioneService;
import com.sm.be.smartbank.service.domain.elem.ProfiloService;
import com.sm.be.smartbank.utils.IbanUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContoServiceImpl implements ContoService {

	// @Value("${app.parameter.paese}")
	private final String codPaese = "IT";

	// @Value("${app.parameter.abi}")
	private final String codAbi = "03076";

	// @Value("${app.parameter.cab}")
	private final String codCab = "02201";

	private final AuthUtil authUtil;
	private final ProfiloService profiloService;
	private final ContoRepository contoRepository;
	private final OperazioneService operazioneService;

	@Override
	public Conto setInsertConto(Profilo profilo) {

		Conto conto = new Conto();
		conto.setProfilo(profilo);
		conto.setNumeroRapporto(generateNumeroRapporto());
		conto.setIban(IbanUtils.buildIban(codPaese, codAbi, codCab, conto.getNumeroRapporto()));
		conto.setSaldo(BigDecimal.valueOf(0));
		conto.setDataCreazione(LocalDateTime.now());

		return contoRepository.save(conto);
	}

	@Override
	public Optional<Conto> getContoAttivo() {

		String username = authUtil.getUsername();
		return contoRepository.findByUsername(username);
	}

	@Override
	public CoordinateBancarieBO getCoordinateBancarie() {

		Optional<Conto> optionalConto = getContoAttivo();
		if (optionalConto.isEmpty())
			return CoordinateBancarieBO.builder().build();
		else {
			Conto conto = optionalConto.get();
			String iban = conto.getIban();

			String codNazionale = iban.substring(0, 2);
			String cinEuropeo = iban.substring(2, 4);

			String cin = iban.substring(4, 5);
			String abi = iban.substring(5, 10);
			String cab = iban.substring(10, 15);
			String numConto = iban.substring(15);

			return CoordinateBancarieBO.builder().iban(iban).codNazionale(codNazionale).cinEuropeo(cinEuropeo).cin(cin)
					.abi(abi).cab(cab).numConto(numConto).build();
		}
	}

	@Override
	public boolean isContoAttivoByIdUtente(Long idUtente) {
		return contoRepository.existsContoAttivoByIdUtente(idUtente);
	}

	@Override
	public Optional<Conto> getContoByIban(String iban) {
		return contoRepository.findByIban(iban);
	}

	@Override
	public int incrementaSaldo(Long idConto, BigDecimal importo) {
		return contoRepository.incrementaSaldo(idConto, importo);
	}

	@Override
	public int decrementaSaldo(Long idConto, BigDecimal importo) {
		return contoRepository.decrementaSaldo(idConto, importo);
	}

	@Override
	public DashBoardBO getDashboard() {

		Profilo profilo = profiloService.getProfilo();
		Optional<Conto> optionalConto = contoRepository.findByIdProfilo(profilo.getIdProfilo());
		if (optionalConto.isEmpty())
			throw new ChkException("Conto non attivo");

		Conto conto = optionalConto.get();

		LocalDate dataFine = LocalDate.now();
		LocalDate dataInizio = dataFine.plusMonths(-1);

		BigDecimal totEntrate = operazioneService.getImportoEntrateNelPerioByIdConto(conto.getIdConto(), dataInizio,
				dataFine);
		BigDecimal totUscite = operazioneService.getImportoUsciteNelPerioByIdConto(conto.getIdConto(), dataInizio,
				dataFine);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return DashBoardBO.builder().saldo(conto.getSaldo()).totEntrateUltimoMese(totEntrate)
				.totUsciteUltimeMese(totUscite).dataInizioUltimoMese(dataInizio.format(formatter))
				.dataFineUltimoMese(dataFine.format(formatter)).build();
	}

	private String generateNumeroRapporto() {
		String prefissoContoCorrente = "CC";
		String numeroRapporto;

		do {
			numeroRapporto = prefissoContoCorrente + Instant.now().getEpochSecond();
		} while (contoRepository.existsByNumeroRapporto(numeroRapporto));

		return numeroRapporto;
	}

}
