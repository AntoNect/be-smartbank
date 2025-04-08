package com.sm.be.smartbank.model.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoordinateBancarieBO {

	private String iban;

	private String codNazionale;

	private String cinEuropeo;

	private String cin;

	private String abi;

	private String cab;

	private String numConto;

}
