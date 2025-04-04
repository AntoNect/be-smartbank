package com.sm.be.smartbank.service.domain.elem;

import java.util.Optional;

import com.sm.be.smartbank.model.bo.PagamentoInputData;
import com.sm.be.smartbank.model.entity.carta.Carta;
import com.sm.be.smartbank.model.entity.operazione.Operazione;
import com.sm.be.smartbank.model.entity.operazione.Pagamento;

public interface PagamentoService {

	Pagamento setInsertPagamento(Operazione operazione, Carta carta, PagamentoInputData inputData);

	Optional<Pagamento> getById(Long idOperazione);

}
