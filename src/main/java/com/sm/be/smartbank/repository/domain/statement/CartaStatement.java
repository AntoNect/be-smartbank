package com.sm.be.smartbank.repository.domain.statement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartaStatement {

	public static final String FIND_BY_NUMERO_AND_MESE_AND_ANNO_AND_CVV = """
			SELECT
				carta
			FROM
				Carta carta
			WHERE
				carta.numero = :numero
				AND TO_CHAR(carta.dataScadenza, 'YYYY') = :anno
				AND TO_CHAR(carta.dataScadenza, 'MM') = :mese
				AND carta.cvv = :cvv
			""";

}
