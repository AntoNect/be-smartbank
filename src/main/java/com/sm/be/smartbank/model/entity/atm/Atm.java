package com.sm.be.smartbank.model.entity.atm;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.be.smartbank.model.entity.localita.Citta;

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
@Table(name = "ATM")
public class Atm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ATM")
	private Long idAtm;

	@Column(name = "ID_CITTA", insertable = false, updatable = false)
	private Long idCitta;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CITTA", nullable = false)
	private Citta citta;

	@Column(name = "NOME", nullable = false, columnDefinition = "TEXT")
	private String nome;

	@Column(name = "INDIRIZZO", columnDefinition = "TEXT")
	private String indirizzo;

	@Column(name = "ID_BANCA", insertable = false, updatable = false)
	private Long idBanca;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_BANCA", nullable = false)
	private Banca banca;

	@Column(name = "LATITUDE")
	private Double latitude;

	@Column(name = "LONGITUDE")
	private Double longitude;

	@Override
	public String toString() {
		return this.nome + ", " + this.indirizzo + ", " + this.citta.getNome() + " ("
				+ this.citta.getProvincia().getSigla() + ")";
	}
}
