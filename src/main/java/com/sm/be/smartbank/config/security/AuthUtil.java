package com.sm.be.smartbank.config.security;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.sm.be.smartbank.model.entity.personale.Utente;

@Component
public class AuthUtil {

	private static final long TOKEN_EXPIRATION = 900;

	@Value("${security.jwt.secret-key}")
	private String jwtSecretKey;

	@Value("${security.jwt.issuer}")
	private String jwtIssuer;

	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (auth != null && auth.isAuthenticated()) ? auth.getName() : null;
	}

	/**
	 * Dato in input l'oggetto Utente genera e ritorna il JWT Token associato
	 * 
	 * @param utente
	 * @return
	 */
	public JwtToken createJwtToken(Utente utente) {
		Instant now = Instant.now();

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer(jwtIssuer).issuedAt(now)
				.expiresAt(now.plusSeconds((TOKEN_EXPIRATION))).subject(utente.getUsername()).claim("role", "utente")
				.build();

		var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));
		var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return JwtToken.builder().token(encoder.encode(params).getTokenValue()).durata(TOKEN_EXPIRATION)
				.scadenza(claims.getExpiresAt().atZone(ZoneId.systemDefault()).toLocalDateTime()).build();
	}

	/**
	 * Dato in input la password in chiaro la ritorna crypata
	 * 
	 * @param password
	 * @return
	 */
	public String encodePassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
