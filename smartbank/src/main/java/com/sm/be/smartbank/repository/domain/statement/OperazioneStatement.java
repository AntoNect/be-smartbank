package com.sm.be.smartbank.repository.domain.statement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperazioneStatement {

	public static final String FIND_ELENCO_OPERAZIONI = """
			SELECT
				ID_OPERAZIONE,
				IMPORTO,
			  	DATA,
				DESCRIZIONE,
				COD_TIPO
			FROM (
				SELECT
					OPERAZIONE.ID_OPERAZIONE,
			    	OPERAZIONE.IMPORTO IMPORTO,
			      	OPERAZIONE.DATA DATA,
			      	BONIFICO.NOME DESCRIZIONE,
			      	OPERAZIONE.COD_TIPO COD_TIPO
				FROM
			      	OPERAZIONE
			      	INNER JOIN BONIFICO ON OPERAZIONE.ID_OPERAZIONE = BONIFICO.ID_OPERAZIONE
			    WHERE
			      	OPERAZIONE.COD_TIPO IN (1, 2)
			      	AND OPERAZIONE.ID_CONTO = :idConto

			    UNION

			    SELECT
			    	OPERAZIONE.ID_OPERAZIONE,
			      	OPERAZIONE.IMPORTO IMPORTO,
			      	OPERAZIONE.DATA DATA,
			      	PAGAMENTO.ESERCENTE DESCRIZIONE,
			      	OPERAZIONE.COD_TIPO COD_TIPO
			    FROM
			      	OPERAZIONE
			      	INNER JOIN PAGAMENTO ON OPERAZIONE.ID_OPERAZIONE = PAGAMENTO.ID_OPERAZIONE
			    WHERE
			      	OPERAZIONE.COD_TIPO = 3
			      	AND OPERAZIONE.ID_CONTO = :idConto

			    UNION

			    SELECT
			    	OPERAZIONE.ID_OPERAZIONE,
			      	OPERAZIONE.IMPORTO IMPORTO,
			      	OPERAZIONE.DATA DATA,
			      	ATM.NOME DESCRIZIONE,
			      	OPERAZIONE.COD_TIPO COD_TIPO
			    FROM
			      	OPERAZIONE
			      	INNER JOIN SPORTELLO ON OPERAZIONE.ID_OPERAZIONE = SPORTELLO.ID_OPERAZIONE
			      	INNER JOIN ATM ON SPORTELLO.ID_ATM = ATM.ID_ATM
			    WHERE
			      	OPERAZIONE.COD_TIPO IN (4, 5)
			      	AND OPERAZIONE.ID_CONTO = :idConto
			  )
			WHERE
				(:#{#filter.codTipoOperazione} = 0 OR COD_TIPO = :#{#filter.codTipoOperazione})
				AND (CAST(:#{#filter.dataInizio} AS DATE) IS NULL OR DATA BETWEEN CAST(:#{#filter.dataInizio} AS DATE) AND CAST(:#{#filter.dataFine} AS DATE) )
			""";
	
	public static final String SUM_IMPORTO_ENTRATE_NEL_PERIODO_BY_ID_CONTO = """
			SELECT 
				SUM(operazione.importo)
			FROM 
				Operazione operazione
			WHERE 
				operazione.idConto = :idConto
				AND operazione.importo > 0
				AND operazione.data BETWEEN CAST(:dataInizio AS DATE) AND CAST(:dataFine AS DATE)
			""";
	
	public static final String SUM_IMPORTO_USCITE_NEL_PERIODO_BY_ID_CONTO = """
						SELECT 
				SUM(operazione.importo)
			FROM 
				Operazione operazione
			WHERE 
				operazione.idConto = :idConto
				AND operazione.importo < 0
				AND operazione.data BETWEEN CAST(:dataInizio AS DATE) AND CAST(:dataFine AS DATE)
			""";

}
