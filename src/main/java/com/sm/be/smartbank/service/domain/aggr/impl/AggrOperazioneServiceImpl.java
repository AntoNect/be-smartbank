package com.sm.be.smartbank.service.domain.aggr.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.enumeration.TipoOperazioneEnum;
import com.sm.be.smartbank.model.bo.BonificoIngressoInputData;
import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.bo.DepositoInputData;
import com.sm.be.smartbank.model.bo.OperazioneInputData;
import com.sm.be.smartbank.model.bo.PagamentoInputData;
import com.sm.be.smartbank.model.bo.PrelievoInputData;
import com.sm.be.smartbank.model.entity.atm.Atm;
import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Pagamento;
import com.sm.be.smartbank.model.entity.operazione.Sportello;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.service.domain.aggr.AggrOperazioneService;
import com.sm.be.smartbank.service.domain.chk.CartaChk;
import com.sm.be.smartbank.service.domain.chk.ContoChk;
import com.sm.be.smartbank.service.domain.chk.OperazioneChk;
import com.sm.be.smartbank.service.domain.elem.AtmService;
import com.sm.be.smartbank.service.domain.elem.BonificoService;
import com.sm.be.smartbank.service.domain.elem.CartaService;
import com.sm.be.smartbank.service.domain.elem.ContoService;
import com.sm.be.smartbank.service.domain.elem.OperazioneService;
import com.sm.be.smartbank.service.domain.elem.PagamentoService;
import com.sm.be.smartbank.service.domain.elem.SportelloService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AggrOperazioneServiceImpl implements AggrOperazioneService {

	private final AtmService atmService;
	private final ContoService contoService;
	private final CartaService cartaService;
	private final BonificoService bonificoService;
	private final SportelloService sportelloService;
	private final PagamentoService pagamentoService;
	private final OperazioneService operazioneService;

	@Override
	@Transactional
	public Bonifico setInsertOperazioneBonificoInUscita(BonificoUscitaInputData inputData) {
		// verifico la validità della struttura dell'iban inserito
		OperazioneChk.chkIban(inputData.getIbanBeneficiario());
		Optional<Conto> optionalConto = contoService.getContoAttivo();
		if (optionalConto.isEmpty())
			throw new ChkException("Il conto non è abilitato ad effettuare l'operazione richiesta");
		Conto conto = optionalConto.get();
		if (conto.getIban().equalsIgnoreCase(inputData.getIbanBeneficiario()))
			throw new ChkException("Non è possibile fare un bonifico al proprio conto");
		if (conto.getSaldo().compareTo(inputData.getImporto()) < 0)
			throw new ChkException("Saldo insufficiente");
		Operazione operazione = operazioneService.setInsertOperazione(conto, OperazioneInputData.builder()
				.tipoOperazione(TipoOperazioneEnum.BONIFICO_USCITA).importo(inputData.getImporto().negate()).build());
		Bonifico bonifico = bonificoService.setInsertBonificoInUscita(operazione, inputData);
		if (bonifico.getDataAddebito().toLocalDate().equals(LocalDate.now())) {
			// se la data di addebito è la data seguente decremento il saldo dal conto del
			// mittente
			contoService.decrementaSaldo(conto.getIdConto(), inputData.getImporto());
			if (Boolean.TRUE.equals(bonifico.getIstantaneo())) {
				// il bonifico è instantaneo, pertanto accredito il saldo sul conto del
				// destinatario se nostro cliente
				Optional<Conto> optionalContoDestinatario = contoService.getContoByIban(bonifico.getIban());
				if (Boolean.FALSE.equals(optionalContoDestinatario.isEmpty())) {
					// l'iban del destinatario è un cliente presso la nostra banca, procedo ad
					// accreditargli l'importo
					Conto contoDestinatario = optionalContoDestinatario.get();
					contoService.incrementaSaldo(contoDestinatario.getIdConto(), inputData.getImporto());
					// inserisco la richiesta di bonifico in ingresso
					Operazione operazioneDestinatario = operazioneService.setInsertOperazione(contoDestinatario,
							OperazioneInputData.builder().tipoOperazione(TipoOperazioneEnum.BONIFICO_INGRESSO)
									.importo(inputData.getImporto()).build());
					bonificoService.setInsertBonificoInIngresso(operazioneDestinatario,
							BonificoIngressoInputData.builder().nomeMittente(conto.getProfilo().getNomeCompleto())
									.importo(bonifico.getOperazione().getImporto()).causale(bonifico.getCausale())
									.istantaneo(bonifico.getIstantaneo()).dataAccredito(bonifico.getDataAccredito())
									.build());
				} else {
					// il destinatario non è nostro cliente: richiamare un ws esterno per l'invio
					// del bonifico
				}
			}
		}
		return bonifico;
	}

	@Override
	@Transactional
	public Long setInsertOperazionePagamento(PagamentoInputData inputData) {

		inputData.setAnnoScadenzaCarta("20" + inputData.getAnnoScadenzaCarta());

		// verifico che i dati inseriti corrispondono ad una vera carta
		Optional<Carta> optionalCarta = cartaService.getByNumeroAndMeseAndAnnoAndCvv(inputData.getNumeroCarta(),
				inputData.getMeseScadenzaCarta(), inputData.getAnnoScadenzaCarta(), inputData.getCvvCarta());
		if (optionalCarta.isEmpty())
			throw new ChkException("Dati carta errati");

		// verifico che la carta sia attiva
		Carta carta = optionalCarta.get();
		CartaChk.chkCartaAttiva(carta);

		// verifico che il conto sia attivo
		Conto conto = carta.getConto();
		ContoChk.chkContoAttivo(conto);

		if (conto.getSaldo().compareTo(inputData.getImporto()) < 0)
			throw new ChkException("Saldo insufficiente");

		Operazione operazione = operazioneService.setInsertOperazione(conto, OperazioneInputData.builder()
				.tipoOperazione(TipoOperazioneEnum.PAGAMENT0).importo(inputData.getImporto().negate()).build());

		Pagamento pagamento = pagamentoService.setInsertPagamento(operazione, carta, inputData);

		contoService.decrementaSaldo(conto.getIdConto(), inputData.getImporto());

		return pagamento.getIdOperazione();
	}

	@Override
	@Transactional
	public Long setInsertOperazionePrelievo(PrelievoInputData inputData) {

		inputData.setAnnoScadenzaCarta("20" + inputData.getAnnoScadenzaCarta());

		OperazioneChk.chkImportMaxPrelievoSingolaOperazione(inputData.getImporto());

		OperazioneChk.chkImportoSportelloPrelievo(inputData.getImporto());

		// verifico che i dati inseriti corrispondono ad una vera carta
		Optional<Carta> optionalCarta = cartaService.getByNumeroAndMeseAndAnnoAndCvv(inputData.getNumeroCarta(),
				inputData.getMeseScadenzaCarta(), inputData.getAnnoScadenzaCarta(), inputData.getCvvCarta());
		if (optionalCarta.isEmpty())
			throw new ChkException("Dati carta errati");

		// recupero l'ATM
		Optional<Atm> optionalAtm = atmService.getById(inputData.getIdAtm());
		if (optionalAtm.isEmpty())
			throw new ChkException("Atm non abilitato ad eseguire l'operazione richiesta");
		Atm atm = optionalAtm.get();

		// verifico che la carta sia attiva
		Carta carta = optionalCarta.get();
		CartaChk.chkCartaAttiva(carta);

		// verifico che il conto sia attivo
		Conto conto = carta.getConto();
		ContoChk.chkContoAttivo(conto);

		if (conto.getSaldo().compareTo(BigDecimal.valueOf(inputData.getImporto())) < 0)
			throw new ChkException("Saldo insufficiente");

		// Verifico che non si superi il limite massimo mensile dei prelievi
		LocalDate dataCorrente = LocalDate.now();
		String codMese = (dataCorrente.getMonthValue() < 10 ? "0" : "") + String.valueOf(dataCorrente.getMonthValue());
		String anno = String.valueOf(dataCorrente.getYear());
		BigDecimal sum = sportelloService.getSumImportoPrelieviMeseCorrente(conto.getIdConto(), codMese, anno);
		OperazioneChk.chkImportMaxPrelievoMeseCorrente(sum, inputData.getImporto());

		Operazione operazione = operazioneService.setInsertOperazione(conto,
				OperazioneInputData.builder().tipoOperazione(TipoOperazioneEnum.PRELIEVO)
						.importo(BigDecimal.valueOf(inputData.getImporto()).negate()).build());

		Sportello sportello = sportelloService.setInsertSportello(operazione, atm);

		contoService.decrementaSaldo(conto.getIdConto(), BigDecimal.valueOf(inputData.getImporto()));

		return sportello.getIdOperazione();
	}

	@Override
	@Transactional
	public Long setInsertOperazioneDeposito(DepositoInputData inputData) {

		inputData.setAnnoScadenzaCarta("20" + inputData.getAnnoScadenzaCarta());

		OperazioneChk.chkImportMaxDepositoSingolaOperazione(inputData.getImporto());

		OperazioneChk.chkImportoSportelloDeposito(inputData.getImporto());

		// verifico che i dati inseriti corrispondono ad una vera carta
		Optional<Carta> optionalCarta = cartaService.getByNumeroAndMeseAndAnnoAndCvv(inputData.getNumeroCarta(),
				inputData.getMeseScadenzaCarta(), inputData.getAnnoScadenzaCarta(), inputData.getCvvCarta());
		if (optionalCarta.isEmpty())
			throw new ChkException("Dati carta errati");

		// recupero l'ATM
		Optional<Atm> optionalAtm = atmService.getById(inputData.getIdAtm());
		if (optionalAtm.isEmpty())
			throw new ChkException("Atm non abilitato ad eseguire l'operazione richiesta");
		Atm atm = optionalAtm.get();

		// verifico che la carta sia attiva
		Carta carta = optionalCarta.get();
		CartaChk.chkCartaAttiva(carta);

		// verifico che il conto sia attivo
		Conto conto = carta.getConto();
		ContoChk.chkContoAttivo(conto);

		Operazione operazione = operazioneService.setInsertOperazione(conto,
				OperazioneInputData.builder().tipoOperazione(TipoOperazioneEnum.DEPOSITO)
						.importo(BigDecimal.valueOf(inputData.getImporto())).build());

		Sportello sportello = sportelloService.setInsertSportello(operazione, atm);

		contoService.incrementaSaldo(conto.getIdConto(), BigDecimal.valueOf(inputData.getImporto()));

		return sportello.getIdOperazione();
	}

}
