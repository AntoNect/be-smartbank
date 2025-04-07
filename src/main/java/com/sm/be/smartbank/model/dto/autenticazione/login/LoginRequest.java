package com.sm.be.smartbank.model.dto.autenticazione.login;

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
public class LoginRequest {

	@NotBlank(message = "L'username è obbligatoria")
	private String username;

	@NotBlank(message = "La password è obbligatoria")
	private String password;

}
