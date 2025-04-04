package com.sm.be.smartbank.enumeration;

import java.util.HashMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoOperazioneEnum {

	// @formatter:off
	BONIFICO_USCITA		(1),
	BONIFICO_INGRESSO 	(2),
	PAGAMENT0			(3),
	PRELIEVO 			(4),
	DEPOSITO 			(5);
	// @formatter:on

	private final int value;
	private static final HashMap<Integer, TipoOperazioneEnum> valueMap = new HashMap<>();

	static {
		for (TipoOperazioneEnum aggregator : values()) {
			valueMap.put(aggregator.getValue(), aggregator);
		}
	}

	public static TipoOperazioneEnum fromValue(int value) {
		return valueMap.get(value);
	}

	@Override
	public String toString() {
		switch (value) {
		case 1: {
			return "Bonifico in Uscita";
		}
		case 2: {
			return "Bonifico in Ingresso";
		}
		case 3: {
			return "Pagamento";
		}
		case 4: {
			return "Prelievo";
		}
		case 5: {
			return "Deposito";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
	}

}
