package com.sm.be.smartbank.model.entity.personale;

import java.time.LocalDate;

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
@Table(name = "PROFILO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profilo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFILO")
	private Long idProfilo;

	@Column(name = "ID_UTENTE", insertable = false, updatable = false)
	private Long idUtente;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_UTENTE", nullable = false)
	private Utente utente;

	@Column(name = "NOME", nullable = false, length = 100)
	private String nome;

	@Column(name = "COGNOME", length = 100)
	private String cognome;

	@Column(name = "CODICE_FISCALE", unique = true, length = 16)
	private String codiceFiscale;

	@Column(name = "TELEFONO", length = 20)
	private String telefono;

	@Column(name = "INDIRIZZO", length = 255)
	private String indirizzo;

	@Column(name = "ID_CITTA_RESIDENZA", insertable = false, updatable = false)
	private Long idCittaResidenza;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CITTA_RESIDENZA", nullable = false)
	private Citta cittaResidenza;

	@Column(name = "DATA_NASCITA", nullable = false)
	private LocalDate dataNascita;

	public String getNomeCompleto() {
		return new StringBuilder().append(this.nome).append(" ").append(this.cognome).toString();
	}

}
