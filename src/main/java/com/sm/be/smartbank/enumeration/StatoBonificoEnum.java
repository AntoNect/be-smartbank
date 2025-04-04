package com.sm.be.smartbank.enumeration;

import java.util.HashMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatoBonificoEnum {

	// @formatter:off
	PRENOTATO		(1), // bonifico standard o programmato la cui data addebito è futura
	IN_ATTESA		(2), // bonifico la cui data addebito è passata (importo sottratto al mittente), ma la data accredito è futura (importo non ancora accreditato al destinatario)
	CONTABILIZZATO	(3), // bonifico il cui importo è arrivato al destinatario
	ANNULLATO		(4), // bonifico programmato annullato dall'utente
	ERRORE			(5); // bonifico in attesa o prenotato andato in errore in fase di esecuzione a causa del saldo insufficiente
	// @formatter:on

	private final int value;
	private static final HashMap<Integer, StatoBonificoEnum> valueMap = new HashMap<>();

	static {
		for (StatoBonificoEnum aggregator : values()) {
			valueMap.put(aggregator.getValue(), aggregator);
		}
	}

	public static StatoBonificoEnum fromValue(int value) {
		return valueMap.get(value);
	}

	@Override
	public String toString() {
		switch (this.value) {
		case 1: {
			return "Prenotato";
		}
		case 2: {
			return "In Attesa";
		}
		case 3: {
			return "Contabilizzato";
		}
		case 4: {
			return "Annullato";
		}
		case 5: {
			return "In Errore";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.value);
		}
	}

}
