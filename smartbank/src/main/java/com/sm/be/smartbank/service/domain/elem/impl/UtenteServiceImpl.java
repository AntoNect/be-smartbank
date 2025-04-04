package com.sm.be.smartbank.service.domain.elem.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.be.smartbank.config.exception.custom.AuthException;
import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.config.security.AuthUtil;
import com.sm.be.smartbank.config.security.JwtToken;
import com.sm.be.smartbank.model.bo.SignupInputData;
import com.sm.be.smartbank.model.bo.filter.LoginFilter;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginReponse;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupResponse;
import com.sm.be.smartbank.model.entity.personale.Utente;
import com.sm.be.smartbank.repository.domain.UtenteRepository;
import com.sm.be.smartbank.service.domain.elem.ContoService;
import com.sm.be.smartbank.service.domain.elem.UtenteService;

@Service
public class UtenteServiceImpl implements UtenteService, UserDetailsService {

	private final AuthUtil authUtil;
	private final UtenteRepository utenteRepository;
	private final ContoService contoService;
	private final AuthenticationManager authenticationManager;

	public UtenteServiceImpl(@Lazy AuthenticationManager authenticationManager, UtenteRepository utenteRepository,
			AuthUtil authUtil, ContoService contoService) {
		this.contoService = contoService;
		this.authenticationManager = authenticationManager;
		this.utenteRepository = utenteRepository;
		this.authUtil = authUtil;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utente utente = utenteRepository.findByUsername(username);
		if (utente != null)
			return User.withUsername(utente.getUsername()).password(utente.getPassword()).build();
		return null;
	}

	@Override
	public Utente getByUsername(String username) {
		return utenteRepository.findByUsername(username);
	}

	@Override
	public SignupResponse signup(SignupInputData filter) {

		if (utenteRepository.existsByEmail(filter.getEmail()))
			throw new ChkException("{validita-indirizzo-mail-presente}");

		if (utenteRepository.existsByUsername(filter.getUsername()))
			throw new ChkException("{validita-username-presente}");

		Utente utente = new Utente();
		utente.setEmail(filter.getEmail());
		utente.setUsername(filter.getUsername());
		utente.setPassword(authUtil.encodePassword(filter.getPassword()));
		utente.setDataRegistrazione(LocalDateTime.now());

		utente = utenteRepository.save(utente);

		return SignupResponse.builder().idUtente(utente.getIdUtente()).email(utente.getEmail())
				.username(utente.getUsername()).build();
	}

	@Override
	public LoginReponse login(LoginFilter filter) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(filter.getUsername(), filter.getPassword()));
			Utente utente = utenteRepository.findByUsername(filter.getUsername());
			JwtToken jwtToken = authUtil.createJwtToken(utente);
			boolean isContoAttivo = contoService.isContoAttivoByIdUtente(utente.getIdUtente());

			return LoginReponse.builder().username(utente.getUsername()).token(jwtToken.getToken())
					.scadenza(jwtToken.getScadenza()).durata(jwtToken.getDurata()).isContoAttivo(isContoAttivo).build();
		} catch (AuthenticationException ae) {
			throw new AuthException("{autorizzazione-credenziali-errate}");
		}
	}

}
