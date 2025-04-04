package com.sm.be.smartbank.utils;

import java.util.Map;

public class IbanUtils {

	// codifica tabella: CARATTERI IN POSIZIONE DISPARI
	private static Map<String, Integer> mapDispari = Map.ofEntries(Map.entry("0", 1), Map.entry("1", 0),
			Map.entry("2", 5), Map.entry("3", 7), Map.entry("4", 9), Map.entry("5", 13), Map.entry("6", 15),
			Map.entry("7", 17), Map.entry("8", 19), Map.entry("9", 21), Map.entry("A", 1), Map.entry("B", 0),
			Map.entry("C", 5), Map.entry("D", 7), Map.entry("E", 9), Map.entry("F", 13), Map.entry("G", 15),
			Map.entry("H", 17), Map.entry("I", 19), Map.entry("J", 21), Map.entry("K", 2), Map.entry("L", 4),
			Map.entry("M", 18), Map.entry("N", 20), Map.entry("O", 11), Map.entry("P", 3), Map.entry("Q", 6),
			Map.entry("R", 8), Map.entry("S", 12), Map.entry("T", 14), Map.entry("U", 16), Map.entry("V", 10),
			Map.entry("W", 22), Map.entry("X", 25), Map.entry("Y", 24), Map.entry("Z", 2));

	// codifica tabella: CARATTERI IN POSIZIONE PARI
	private static Map<String, Integer> mapPari = Map.ofEntries(Map.entry("0", 0), Map.entry("1", 1), Map.entry("2", 2),
			Map.entry("3", 3), Map.entry("4", 4), Map.entry("5", 5), Map.entry("6", 6), Map.entry("7", 7),
			Map.entry("8", 8), Map.entry("9", 9), Map.entry("A", 0), Map.entry("B", 1), Map.entry("C", 2),
			Map.entry("D", 3), Map.entry("E", 4), Map.entry("F", 5), Map.entry("G", 6), Map.entry("H", 7),
			Map.entry("I", 8), Map.entry("J", 9), Map.entry("K", 10), Map.entry("L", 11), Map.entry("M", 12),
			Map.entry("N", 13), Map.entry("O", 14), Map.entry("P", 15), Map.entry("Q", 16), Map.entry("R", 17),
			Map.entry("S", 18), Map.entry("T", 19), Map.entry("U", 20), Map.entry("V", 21), Map.entry("W", 22),
			Map.entry("X", 23), Map.entry("Y", 24), Map.entry("Z", 25));

	// codifica tabella: CIN ITALIANO
	private static String[] arrayCinItaliano = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * Dato in input il codice ABI, il codice CAB e il numero del conto corrente
	 * ritorna il valore del CIN Italiano
	 * 
	 * @param codAbi:   codice identificativo banca
	 * @param codCab:   codice identificativo filiale
	 * @param numConto: codice identificativo conto
	 * @return
	 */
	private static String buildCinItaliano(String codAbi, String codCab, String numConto) {

		String bban = " " + codAbi + codCab + numConto;
		int sommaDispari = 0;
		int sommaPari = 0;

		for (int i = 0; i < bban.length(); i++) {

			if (i == 0)
				continue;

			char c = bban.charAt(i);

			if (i % 2 == 1) {
				// posizione dispari
				sommaDispari += mapDispari.get(String.valueOf(c));
			} else {
				// posizione pari
				sommaPari += mapPari.get(String.valueOf(c));
			}
		}

		return arrayCinItaliano[(sommaDispari + sommaPari) % 26];
	}

	// codifica tabella: CIN EUROPEO
	private static Map<String, Integer> mapCinEuropeo = Map.ofEntries(Map.entry("A", 10), Map.entry("B", 11),
			Map.entry("C", 12), Map.entry("D", 13), Map.entry("E", 14), Map.entry("F", 15), Map.entry("G", 16),
			Map.entry("H", 17), Map.entry("I", 18), Map.entry("J", 19), Map.entry("K", 20), Map.entry("L", 21),
			Map.entry("M", 22), Map.entry("N", 23), Map.entry("O", 24), Map.entry("P", 25), Map.entry("Q", 26),
			Map.entry("R", 27), Map.entry("S", 28), Map.entry("T", 29), Map.entry("U", 30), Map.entry("V", 31),
			Map.entry("W", 32), Map.entry("X", 33), Map.entry("Y", 34), Map.entry("Z", 35));

	/**
	 * Dato in input il codice Nazionale, il codice CIN Italiano, il codice ABI, il
	 * codice CAB e il numero del conto corrente ritorna il CIN Europeo
	 * 
	 * @param codNazionale: il codice della nazione in cui la banca ha sede (es. IT)
	 * @param cinItaliano:  codice CIN Italiano
	 * @param codAbi:       codice identificativo banca
	 * @param codCab:       codice identificativo filiale
	 * @param numConto:     codice identificativo conto
	 * @return
	 */
	private static String buildCinEuropeo(String codNazionale, String codAbi, String codCab, String numConto) {

		String cinItaliano = buildCinItaliano(codAbi, codCab, numConto);

		String reverseIban = cinItaliano + codAbi + codCab + numConto + codNazionale + "00";
		StringBuilder sb = new StringBuilder("0");

		for (int i = 0; i < reverseIban.length(); i++) {
			char c = reverseIban.charAt(i);

			if (Character.isLetter(c)) {
				// è una lettera, mi recupero il valore di riferimento
				Integer value = mapCinEuropeo.get(String.valueOf(c));
				sb.append(value);
			} else {
				sb.append(c);
			}

			if (sb.length() >= 10)
				sb = new StringBuilder(String.valueOf(Long.valueOf(sb.toString()) % 97));

		}

		Long s = Long.valueOf(sb.toString());
		if (s >= 98)
			s = s % 97;

		return String.valueOf(98 - s);
	}

	public static String buildIban(String codNazionale, String codAbi, String codCab, String numConto) {
		String cinEuropeo = buildCinEuropeo(codNazionale, codAbi, codCab, numConto);
		String cinItaliano = buildCinItaliano(codAbi, codCab, numConto);

		return codNazionale + cinEuropeo + cinItaliano + codAbi + codCab + numConto;
	}

}
