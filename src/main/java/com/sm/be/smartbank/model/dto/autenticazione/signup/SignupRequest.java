package com.sm.be.smartbank.model.dto.autenticazione.signup;

import jakarta.validation.constraints.NotBlank;
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
public class SignupRequest {

	@NotBlank(message = "L'indirizzo mail è obbligatorio")
	private String email;

	@NotBlank(message = "L'username è obbligatoria")
	private String username;

	@NotBlank(message = "La password è obbligatoria")
	private String password;

}
