package com.sm.be.smartbank.service.domain.elem;

import java.util.Optional;

import com.sm.be.smartbank.model.entity.atm.Atm;

public interface AtmService {

	Optional<Atm> getById(Long id);

}
