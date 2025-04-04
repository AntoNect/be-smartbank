package com.sm.be.smartbank.service.domain.elem;

import java.util.Optional;

import com.sm.be.smartbank.model.entity.localita.Citta;

public interface CittaService {

	Optional<Citta> getById(Long id);

}
