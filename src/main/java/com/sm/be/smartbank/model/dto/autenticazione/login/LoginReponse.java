package com.sm.be.smartbank.model.dto.autenticazione.login;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginReponse {

	private String username;

	private long durata;

	private LocalDateTime scadenza;

	private String token;
	
	private boolean isContoAttivo;

}
