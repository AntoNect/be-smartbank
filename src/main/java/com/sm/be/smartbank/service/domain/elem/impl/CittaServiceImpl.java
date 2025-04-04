package com.sm.be.smartbank.service.domain.elem.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.model.entity.localita.Citta;
import com.sm.be.smartbank.repository.domain.CittaRepository;
import com.sm.be.smartbank.service.domain.elem.CittaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CittaServiceImpl implements CittaService {

	private final CittaRepository cittaRepository;

	@Override
	public Optional<Citta> getById(Long id) {
		return cittaRepository.findById(id);
	}

}
