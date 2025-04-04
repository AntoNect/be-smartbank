package com.sm.be.smartbank.service.domain.elem.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.model.bo.PagamentoInputData;
import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Pagamento;
import com.sm.be.smartbank.repository.domain.PagamentoRepository;
import com.sm.be.smartbank.service.domain.elem.PagamentoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

	private PagamentoRepository pagamentoRepository;

	@Override
	public Pagamento setInsertPagamento(Operazione operazione, Carta carta, PagamentoInputData inputData) {

		Pagamento pagamento = new Pagamento();
		pagamento.setOperazione(operazione);
		pagamento.setEsercente(inputData.getEsercente());
		pagamento.setCarta(carta);
		
		return pagamentoRepository.save(pagamento);
	}

	@Override
	public Optional<Pagamento> getById(Long idOperazione) {
		return pagamentoRepository.findById(idOperazione);
	}

}
