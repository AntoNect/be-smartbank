package com.sm.be.smartbank.controller.domain.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.be.smartbank.controller.domain.AutenticazioneController;
import com.sm.be.smartbank.mapper.bo.autenticazione.AutenticazioneMapper;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginReponse;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginRequest;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupRequest;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupResponse;
import com.sm.be.smartbank.service.domain.elem.UtenteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AutenticazioneControllerImpl implements AutenticazioneController {

	private UtenteService utenteService;

	AutenticazioneControllerImpl(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	@Override
	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {

		AutenticazioneMapper mapper = Mappers.getMapper(AutenticazioneMapper.class);
		SignupResponse response = utenteService.signup(mapper.toSignupInputData(request));
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<LoginReponse> login(@Valid @RequestBody LoginRequest request) {

		AutenticazioneMapper mapper = Mappers.getMapper(AutenticazioneMapper.class);
		LoginReponse response = utenteService.login(mapper.toLoginFilter(request));
		return ResponseEntity.ok(response);
	}

}
