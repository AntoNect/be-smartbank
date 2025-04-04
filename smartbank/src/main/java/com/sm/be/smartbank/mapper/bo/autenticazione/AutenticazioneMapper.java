package com.sm.be.smartbank.mapper.bo.autenticazione;

import org.mapstruct.Mapper;

import com.sm.be.smartbank.model.bo.SignupInputData;
import com.sm.be.smartbank.model.bo.filter.LoginFilter;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginRequest;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupRequest;

@Mapper
public interface AutenticazioneMapper {

	SignupInputData toSignupInputData(SignupRequest request);

	LoginFilter toLoginFilter(LoginRequest request);

}
