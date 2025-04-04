package com.sm.be.smartbank.mapper.bo.profilo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sm.be.smartbank.model.bo.DashBoardBO;
import com.sm.be.smartbank.model.bo.ProfiloInputData;
import com.sm.be.smartbank.model.dto.profilo.getdashboard.GetDashboardResponse;
import com.sm.be.smartbank.model.dto.profilo.getprofilo.GetProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setinsertprofilo.SetInsertProfiloResponse;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloRequest;
import com.sm.be.smartbank.model.dto.profilo.setupdateprofilo.SetUpdateProfiloResponse;
import com.sm.be.smartbank.model.entity.personale.Profilo;

@Mapper
public interface ProfiloMapper {

	@Mapping(source = "cittaResidenza.nome", target = "cittaResidenza")
	@Mapping(source = "indirizzo", target = "indirizzoResidenza")
	GetProfiloResponse toGetProfiloResponse(Profilo profilo);

	@Mapping(source = "indirizzo", target = "indirizzoResidenza")
	ProfiloInputData toProfiloInputData(SetInsertProfiloRequest request);

	@Mapping(source = "cittaResidenza.nome", target = "cittaResidenza")
	SetInsertProfiloResponse toSetInsertProfiloResponse(Profilo profilo);

	@Mapping(target = "codiceFiscale", ignore = true)
	@Mapping(target = "cognome", ignore = true)
	@Mapping(target = "nome", ignore = true)
	ProfiloInputData toProfiloInputData(SetUpdateProfiloRequest request);

	GetDashboardResponse toGetDashboardResponse(DashBoardBO view);

	@Mapping(source = "cittaResidenza.nome", target = "cittaResidenza")
	@Mapping(source = "indirizzo", target = "indirizzoResidenza")
	SetUpdateProfiloResponse toSetUpdateProfiloResponse(Profilo profilo);

}
