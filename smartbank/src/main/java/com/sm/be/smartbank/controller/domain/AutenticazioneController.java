package com.sm.be.smartbank.controller.domain;

import org.springframework.http.ResponseEntity;

import com.sm.be.smartbank.model.dto.autenticazione.login.LoginReponse;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginRequest;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupRequest;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticazione")
public interface AutenticazioneController {

	public ResponseEntity<SignupResponse> signup(SignupRequest request);

	public ResponseEntity<LoginReponse> login(LoginRequest request);

}
