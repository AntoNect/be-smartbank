package com.sm.be.smartbank.model.dto.autenticazione.signup;

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
public class SignupResponse {

	private Long idUtente;
	private String email;
	private String username;

}
