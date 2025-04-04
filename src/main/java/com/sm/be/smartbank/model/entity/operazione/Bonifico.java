package com.sm.be.smartbank.model.entity.operazione;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BONIFICO")
public class Bonifico {

	@Id
	@Column(name = "ID_OPERAZIONE")
	private Long idOperazione;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId
	@JoinColumn(name = "ID_OPERAZIONE")
	private Operazione operazione;

	@Column(name = "IBAN", length = 27)
	private String iban;

	@Column(name = "NOME", nullable = false, length = 100)
	private String nome;

	@Column(name = "CAUSALE", length = 255)
	private String causale;

	@Column(name = "ISTANTANEO", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean istantaneo;

	@Column(name = "DATA_ACCREDITO")
	private LocalDateTime dataAccredito;

	@Column(name = "DATA_ADDEBITO")
	private LocalDateTime dataAddebito;

	@Column(name = "COD_STATO", nullable = false)
	private Integer codStato;

}
