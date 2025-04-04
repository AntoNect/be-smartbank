package com.sm.be.smartbank.service.domain.elem;

import com.sm.be.smartbank.model.bo.SignupInputData;
import com.sm.be.smartbank.model.bo.filter.LoginFilter;
import com.sm.be.smartbank.model.dto.autenticazione.login.LoginReponse;
import com.sm.be.smartbank.model.dto.autenticazione.signup.SignupResponse;
import com.sm.be.smartbank.model.entity.personale.Utente;

public interface UtenteService {

	SignupResponse signup(SignupInputData filter);

	LoginReponse login(LoginFilter filter);

	Utente getByUsername(String username);

}
