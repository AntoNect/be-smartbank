package com.sm.be.smartbank.repository.domain.statement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SportelloStatement {

	public static final String SUM_IMPORTO_PRELIVEVI_MESE_CORRENTE = """
			SELECT
				SUM(operazione.importo) * -1
			FROM
				Operazione operazione INNER JOIN Sportello sportello on operazione.idOperazione = sportello.idOperazione
			WHERE
				operazione.importo < 0
				AND operazione.idConto = :idConto
				AND TO_CHAR(operazione.data, 'MM') = :mese
				AND TO_CHAR(operazione.data, 'YYYY') = :anno
			""";

}
