package com.sm.be.smartbank.model.entity.carta;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.be.smartbank.model.entity.personale.Conto;

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
@Table(name = "CARTA")
public class Carta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CARTA")
	private Long idCarta;

	@Column(name = "ID_CONTO", insertable = false, updatable = false)
	private Long idConto;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CONTO", nullable = false)
	private Conto conto;

	@Column(name = "NUMERO", nullable = false, unique = true, length = 16)
	private String numero;

	@Column(name = "DATA_SCADENZA", nullable = false)
	private LocalDate dataScadenza;

	@Column(name = "CVV", nullable = false, length = 3)
	private String cvv;

	@Column(name = "DATA_INIZIO", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataInizio;

	@Column(name = "DATA_FINE", columnDefinition = "TIMESTAMP DEFAULT NULL")
	private LocalDateTime dataFine;

	@Column(name = "ID_CIRCUITO", insertable = false, updatable = false)
	private Long idCircuito;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CIRCUITO", nullable = false)
	private Circuito circuito;

}
