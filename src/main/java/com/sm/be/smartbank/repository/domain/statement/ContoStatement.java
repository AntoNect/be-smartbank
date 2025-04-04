package com.sm.be.smartbank.repository.domain.statement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContoStatement {

	public static final String FIND_BY_USERNAME = """
			SELECT
				conto
			FROM
				Conto conto
				JOIN Profilo profilo ON conto.idProfilo = profilo.idProfilo
				JOIN Utente utente ON profilo.idUtente = utente.idUtente
			WHERE
				utente.username = :username
				AND conto.dataChiusura IS NULL
			""";

	public static final String FIND_BY_ID_UTENTE = """
			SELECT EXISTS (
				SELECT
					conto
				FROM
					Conto conto
					JOIN Profilo profilo ON conto.idProfilo = profilo.idProfilo
					JOIN Utente utente ON profilo.idUtente = utente.idUtente
				WHERE
					utente.idUtente = :idUtente
					AND conto.dataChiusura IS NULL
			)
			""";

	public static final String INCREMENTA_SALDO = """
			UPDATE
				Conto conto
			SET
				conto.saldo = conto.saldo + :importo
			WHERE
				conto.idConto = :idConto
			""";

	public static final String DECREMENTA_SALDO = """
			UPDATE
				Conto conto
			SET
				conto.saldo = conto.saldo - :importo
			WHERE
				conto.idConto = :idConto
			""";

	public static final String FIND_DASHBOARD = """
			SELECT
				conto.saldo saldo,
			""";

}
