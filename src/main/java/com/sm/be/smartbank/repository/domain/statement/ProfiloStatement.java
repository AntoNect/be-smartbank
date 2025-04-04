package com.sm.be.smartbank.repository.domain.statement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfiloStatement {

	public static final String FIND_BY_USERNAME = """
			SELECT
				profilo
			FROM
				Profilo profilo
				JOIN Utente utente ON profilo.idUtente = utente.idUtente
			WHERE
				utente.username = :username
			""";

}
