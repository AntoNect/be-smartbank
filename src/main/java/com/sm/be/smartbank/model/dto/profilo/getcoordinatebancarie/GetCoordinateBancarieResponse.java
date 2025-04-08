package com.sm.be.smartbank.model.dto.profilo.getcoordinatebancarie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetCoordinateBancarieResponse {

	private String iban;

	private String codNazionale;

	private String cinEuropeo;

	private String cin;

	private String abi;

	private String cab;

	private String numConto;

}
