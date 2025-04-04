package com.sm.be.smartbank.config.security;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtToken {

	private long durata;

	private LocalDateTime scadenza;

	private String token;

}
