package com.sm.be.smartbank.service.domain.elem.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sm.be.smartbank.model.entity.atm.Atm;
import com.sm.be.smartbank.repository.domain.AtmRepository;
import com.sm.be.smartbank.service.domain.elem.AtmService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

	private final AtmRepository atmRepository;
	
	@Override
	public Optional<Atm> getById(Long id) {
		return atmRepository.findById(id);
	}

}
