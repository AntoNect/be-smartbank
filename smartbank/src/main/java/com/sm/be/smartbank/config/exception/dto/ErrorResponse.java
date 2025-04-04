package com.sm.be.smartbank.config.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

	private int code;
	
	private String status;
	
	private String message;

}
