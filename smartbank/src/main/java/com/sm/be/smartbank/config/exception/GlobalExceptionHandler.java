package com.sm.be.smartbank.config.exception;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sm.be.smartbank.config.exception.custom.AuthException;
import com.sm.be.smartbank.config.exception.custom.ChkException;
import com.sm.be.smartbank.config.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Intercetto tutti gli AuthException lanciati dai vari endpoint, al fine di
	 * personalizzare la response
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
		logger.error("AuthException catturata: {}", ex.getMessage(), ex);
		String message = buildMessageContext(ex.getMessage());
		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		ErrorResponse response = ErrorResponse.builder().status(httpStatus.getReasonPhrase()).code(httpStatus.value())
				.message(message).build();
		return new ResponseEntity<>(response, httpStatus);
	}

	@ExceptionHandler(ChkException.class)
	public ResponseEntity<ErrorResponse> handleChkException(ChkException ex) {
		logger.error("ChkException catturata: {}", ex.getMessage(), ex);

		String message = buildMessageContext(ex.getMessage());

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse response = ErrorResponse.builder().status(httpStatus.getReasonPhrase()).code(httpStatus.value())
				.message(message).build();
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * Intercetto tutti gli MethodArgumentNotValidException lanciati dai vari
	 * endpoint, al fine di personalizzare la response
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		logger.error("MethodArgumentNotValidException catturata: {}", ex.getMessage(), ex);

		BindingResult bindingResult = ex.getBindingResult();

		// Recupero i campi non validi
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		// Creo il messaggio con i campi non validi e il motivo
		String message = fieldErrors.stream().findFirst().get().getDefaultMessage();

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse response = ErrorResponse.builder().status(httpStatus.getReasonPhrase()).code(httpStatus.value())
				.message(message).build();
		return new ResponseEntity<>(response, httpStatus);
	}

	/**
	 * Dato in input la key del messaggio, recupero il valore dal file
	 * messages/error-messages.properties
	 * 
	 * @param message
	 * @return
	 */
	private String buildMessageContext(String message) {
		try {
			String keyMessage = message.replace("{", "").replace("}", "");
			return messageSource.getMessage(keyMessage, null, Locale.ITALIAN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
