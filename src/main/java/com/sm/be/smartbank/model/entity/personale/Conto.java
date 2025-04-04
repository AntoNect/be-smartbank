package com.sm.be.smartbank.model.entity.personale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "CONTO")
public class Conto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONTO")
	private Long idConto;

	@Column(name = "ID_PROFILO", insertable = false, updatable = false)
	private Long idProfilo;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_PROFILO", nullable = false)
	private Profilo profilo;

	@Column(name = "SALDO", nullable = false, precision = 15, scale = 2)
	private BigDecimal saldo;

	@Column(name = "NUMERO_RAPPORTO", nullable = false, length = 12)
	private String numeroRapporto;

	@Column(name = "IBAN", nullable = false, length = 27)
	private String iban;

	@Column(name = "DATA_CREAZIONE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCreazione;

	@Column(name = "DATA_CHIUSURA", columnDefinition = "TIMESTAMP DEFAULT NULL")
	private LocalDateTime dataChiusura;

}
