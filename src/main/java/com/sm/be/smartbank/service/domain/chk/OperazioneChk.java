package com.sm.be.smartbank.service.domain.chk;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.iban4j.Iban;
import org.iban4j.IbanFormatException;
import org.iban4j.InvalidCheckDigitException;

import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.enumeration.TipoOperazioneEnum;
import com.sm.be.smartbank.model.bo.BonificoUscitaInputData;
import com.sm.be.smartbank.model.bo.filter.GetElencoOperazioniFilter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperazioneChk {

	private static final int MAX_DEPOSITO_SINGOLA_OPERAZIONE = 4000;
	private static final int MAX_PRELIEVO_SINGOLA_OPERAZIONE = 5000;
	private static final int MAX_PRELIEVO_NEL_MESE = 7000;
	private static final List<Integer> TAGLI_VALIDI_PRELIEVO = List.of(50, 20);
	private static final List<Integer> TAGLI_VALIDI_DEPOSITO = List.of(5, 10, 20, 50, 100, 200);

	public static void chkIban(String iban) {
		try {
			Iban.valueOf(iban);
		} catch (IbanFormatException | InvalidCheckDigitException e) {
			throw new ChkException("{validita-iban}");
		}
	}

	public static void chkGetElencoOperazioni(GetElencoOperazioniFilter filter) {

		if (filter.getCodTipoOperazione() > 0 && TipoOperazioneEnum.fromValue(filter.getCodTipoOperazione()) == null)
			throw new ChkException("Cod tipo operazione non valido");

		if (filter.getDataInizio() != null && filter.getDataFine() == null)
			throw new ChkException("Data fine obbligatoria");

		if (filter.getDataInizio() == null && filter.getDataFine() != null)
			throw new ChkException("Data inizio obbligatoria");

		if (filter.getDataInizio() != null && filter.getDataFine() != null
				&& filter.getDataInizio().isAfter(filter.getDataFine()))
			throw new ChkException("La data inizio deve essere minore della data fine");

	}

	public static void chkSetInsertBonifico(BonificoUscitaInputData inputData) {

		if (inputData.isIstantaneo() && inputData.getDataAddebito() != null) {
			throw new ChkException("La data addebito non deve essere valorizzata per i bonifici istantanei");
		}

		if (inputData.getDataAddebito() != null) {
			// data addebito valorizzata

			if (inputData.getDataAddebito().isBefore(LocalDate.now()))
				throw new ChkException("La data addebito non può essere minore della data corrente");

			if (inputData.getDataAddebito().getDayOfWeek().equals(DayOfWeek.SATURDAY)
					|| inputData.getDataAddebito().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				throw new ChkException("La data addebito non può essere un giorno festivo");
			}

			if (inputData.getDataAddebito().equals(LocalDate.now()) && LocalDateTime.now().getHour() > 16) {
				throw new ChkException(
						"Non è possibile effettuare il bonifico nella data odierna in quanto sono superate le ore 16");
			}
		}

	}

	/**
	 * Dato in input l'importo di un deposito verifico se rientra nei limiti massimi
	 * previsti dalla legge. Il valore è contenuto nella variabile
	 * MAX_PRELIEVO_SINGOLA_OPERAZIONE
	 * 
	 * @param importo
	 */
	public static void chkImportMaxDepositoSingolaOperazione(int importo) {
		if (importo > MAX_DEPOSITO_SINGOLA_OPERAZIONE)
			throw new ChkException("Valore importo troppo elevato");
	}

	/**
	 * Dato in input l'importo di un prelievo verifico se rientra nei limiti massimi
	 * previsti dalla legge. Il valore è contenuto nella variabile
	 * MAX_PRELIEVO_SINGOLA_OPERAZIONE
	 * 
	 * @param importo
	 */
	public static void chkImportMaxPrelievoSingolaOperazione(int importo) {
		if (importo > MAX_PRELIEVO_SINGOLA_OPERAZIONE)
			throw new ChkException("Valore importo troppo elevato");
	}

	public static void chkImportMaxPrelievoMeseCorrente(BigDecimal somma, int importo) {
		
		if (somma == null)
			somma = BigDecimal.valueOf(0);
		
		if (somma.add(BigDecimal.valueOf(importo)).compareTo(BigDecimal.valueOf(MAX_PRELIEVO_NEL_MESE)) > 0)
			throw new ChkException("E' stato superato il limite massimo prelevabile in un singolo mese");
	}

	/**
	 * Dato in input l'importo di un prelievo verifica se è possibile risalire al
	 * valore inserito sommando i tagli validi. I tagli validi sono definiti nella
	 * lista TAGLI_VALIDI_PRELIEVO
	 * 
	 * @param importo
	 */
	public static void chkImportoSportelloPrelievo(int importo) {

		if (Boolean.FALSE.equals(isComponibile(importo, TAGLI_VALIDI_PRELIEVO)))
			throw new ChkException("Importo non valido");

	}

	/**
	 * Dato in input l'importo di un deposito verifica se è possibile risalire al
	 * valore inserito sommando i tagli validi. I tagli validi sono definiti nella
	 * lista TAGLI_VALIDI_DEPOSITO
	 * 
	 * @param importo
	 */
	public static void chkImportoSportelloDeposito(int importo) {

		if (Boolean.FALSE.equals(isComponibile(importo, TAGLI_VALIDI_DEPOSITO)))
			throw new ChkException("Importo non valido");

	}

	private static boolean isComponibile(int importo, List<Integer> tagli) {
		return verificaCombinazioni(importo, tagli, 0);
	}

	private static boolean verificaCombinazioni(int importo, List<Integer> tagli, int index) {
		// Se abbiamo raggiunto l'importo esatto
		if (importo == 0)
			return true;

		// Se abbiamo superato l'importo o finito i tagli
		if (importo < 0 || index >= tagli.size())
			return false;

		// Provo a prendere banconote del taglio corrente
		int taglioCorrente = tagli.get(index);

		// Provo tutte le quantità possibili per il taglio attuale
		for (int n = 0; n <= importo / taglioCorrente; n++) {
			int nuovoImporto = importo - (n * taglioCorrente);

			// Ricorsione per provare i tagli rimanenti
			if (verificaCombinazioni(nuovoImporto, tagli, index + 1)) {
				return true;
			}
		}

		return false;
	}

}
