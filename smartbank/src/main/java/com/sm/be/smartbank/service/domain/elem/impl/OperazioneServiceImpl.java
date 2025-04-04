package com.sm.be.smartbank.service.domain.elem.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.enumeration.StatoBonificoEnum;
import com.sm.be.smartbank.enumeration.TipoOperazioneEnum;
import com.sm.be.smartbank.model.bo.ElencoOperazioniBO;
import com.sm.be.smartbank.model.bo.OperazioneInputData;
import com.sm.be.smartbank.model.bo.filter.GetElencoOperazioniFilter;
import com.sm.be.smartbank.model.bo.view.ElencoOperazioniView;
import com.sm.be.smartbank.model.dto.BonificoDTO;
import com.sm.be.smartbank.model.dto.OperazioneDTO;
import com.sm.be.smartbank.model.dto.PagamentoDTO;
import com.sm.be.smartbank.model.dto.SportelloDTO;
import com.sm.be.smartbank.model.entity.operazione.Bonifico;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Pagamento;
import com.sm.be.smartbank.model.entity.operazione.Sportello;
import com.sm.be.smartbank.model.entity.personale.Conto;
import com.sm.be.smartbank.repository.domain.OperazioneRepository;
import com.sm.be.smartbank.service.domain.chk.OperazioneChk;
import com.sm.be.smartbank.service.domain.elem.BonificoService;
import com.sm.be.smartbank.service.domain.elem.ContoService;
import com.sm.be.smartbank.service.domain.elem.OperazioneService;
import com.sm.be.smartbank.service.domain.elem.PagamentoService;
import com.sm.be.smartbank.service.domain.elem.SportelloService;

@Service
public class OperazioneServiceImpl implements OperazioneService {

	private final ContoService contoService;
	private final BonificoService bonificoService;
	private final PagamentoService pagamentoService;
	private final SportelloService sportelloService;
	private final OperazioneRepository operazioneRepository;

	public OperazioneServiceImpl(@Lazy ContoService contoService, OperazioneRepository operazioneRepository,
			PagamentoService pagamentoService, SportelloService sportelloService, BonificoService bonificoService) {
		this.contoService = contoService;
		this.bonificoService = bonificoService;
		this.pagamentoService = pagamentoService;
		this.sportelloService = sportelloService;
		this.operazioneRepository = operazioneRepository;
	}

	@Override
	public Operazione setInsertOperazione(Conto conto, OperazioneInputData inputData) {

		Operazione operazione = new Operazione();
		operazione.setConto(conto);
		operazione.setCodTipo(inputData.getTipoOperazione().getValue());
		operazione.setImporto(inputData.getImporto());
		operazione.setData(inputData.getData() != null ? inputData.getData() : LocalDateTime.now());

		return operazioneRepository.save(operazione);
	}

	@Override
	public Slice<ElencoOperazioniBO> getElencoOperazioni(GetElencoOperazioniFilter filter, Pageable pageable) {

		OperazioneChk.chkGetElencoOperazioni(filter);

		Optional<Conto> optionalConto = contoService.getContoAttivo();
		if (optionalConto.isEmpty())
			throw new ChkException("conto non attivo");

		Long idConto = optionalConto.get().getIdConto();

		Slice<ElencoOperazioniView> elencoView = operazioneRepository.findElencoOperazioni(idConto, filter, PageRequest
				.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.unsafe(Sort.Direction.DESC, "DATA")));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		List<ElencoOperazioniBO> elencoBO = new ArrayList<>();
		for (ElencoOperazioniView operazione : elencoView.getContent()) {
			String dataOperazione = operazione.getData().format(formatter);
			elencoBO.add(ElencoOperazioniBO.builder().idOperazione(operazione.getIdOperazione())
					.importo(operazione.getImporto()).data(dataOperazione).descrizione(operazione.getDescrizione())
					.codTipo(operazione.getCodTipo()).build());
		}

		return new SliceImpl<>(elencoBO, pageable, elencoView.hasNext());
	}

	@Override
	public BigDecimal getImportoEntrateNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine) {
		return operazioneRepository.sumImportoEntrateNelPerioByIdConto(idConto, dataInizio, dataFine);
	}

	@Override
	public BigDecimal getImportoUsciteNelPerioByIdConto(Long idConto, LocalDate dataInizio, LocalDate dataFine) {
		return operazioneRepository.sumImportoUsciteNelPerioByIdConto(idConto, dataInizio, dataFine);
	}

	@Override
	public OperazioneDTO getOperazione(Long idOperazione) {

		Optional<Operazione> optionalOperazione = operazioneRepository.findById(idOperazione);
		if (optionalOperazione.isEmpty())
			throw new ChkException("operazione non trovata");
		Operazione operazione = optionalOperazione.get();

		Optional<Conto> optionalConto = contoService.getContoAttivo();
		if (optionalConto.isEmpty())
			throw new ChkException("conto non attivo");
		Conto conto = optionalConto.get();

		BonificoDTO bonificoDTO = null;
		PagamentoDTO pagamentoDTO = null;
		SportelloDTO sportelloDTO = null;
		if (!Objects.equals(operazione.getIdConto(), conto.getIdConto()))
			throw new ChkException("L'utente non è abilitato a visualizzare l'operazione richiesta");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		DateTimeFormatter formatterSoloData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (operazione.getCodTipo() == TipoOperazioneEnum.BONIFICO_INGRESSO.getValue()
				|| operazione.getCodTipo() == TipoOperazioneEnum.BONIFICO_USCITA.getValue()) {
			Optional<Bonifico> optionalBonifico = bonificoService.getById(idOperazione);
			if (optionalBonifico.isEmpty())
				throw new ChkException("operazione non trovata");

			Bonifico bonifico = optionalBonifico.get();
			String dataAddebito = bonifico.getDataAddebito() != null
					? bonifico.getDataAddebito().format(formatterSoloData)
					: null;
			String dataAccredito = bonifico.getDataAccredito() != null
					? bonifico.getDataAccredito().format(formatterSoloData)
					: null;

			bonificoDTO = BonificoDTO.builder().iban(bonifico.getIban()).nome(bonifico.getNome())
					.causale(bonifico.getCausale()).istantaneo(bonifico.getIstantaneo()).dataAccredito(dataAccredito)
					.dataAddebito(dataAddebito)
					.descStato(StatoBonificoEnum.fromValue(bonifico.getCodStato()).toString()).build();
		} else if (operazione.getCodTipo() == TipoOperazioneEnum.PAGAMENT0.getValue()) {
			Optional<Pagamento> optionalPagamento = pagamentoService.getById(idOperazione);
			if (optionalPagamento.isEmpty())
				throw new ChkException("operazione non trovata");

			Pagamento pagamento = optionalPagamento.get();
			pagamentoDTO = PagamentoDTO.builder().esercente(pagamento.getEsercente()).build();
		} else if (operazione.getCodTipo() == TipoOperazioneEnum.PRELIEVO.getValue()
				|| operazione.getCodTipo() == TipoOperazioneEnum.DEPOSITO.getValue()) {
			Optional<Sportello> optionalSportello = sportelloService.getById(idOperazione);
			if (optionalSportello.isEmpty())
				throw new ChkException("operazione non trovata");

			Sportello sportello = optionalSportello.get();
			sportelloDTO = SportelloDTO.builder().descAtm(sportello.getAtm().toString()).build();
		}

		return OperazioneDTO.builder().idOperazione(operazione.getIdOperazione()).importo(operazione.getImporto())
				.data(operazione.getData().format(formatter)).codTipoOperazione(operazione.getCodTipo())
				.descTipoOperazione(TipoOperazioneEnum.fromValue(operazione.getCodTipo()).toString())
				.bonifico(bonificoDTO).pagamento(pagamentoDTO).sportello(sportelloDTO).build();
	}

}
