	package com.sm.be.smartbank.model.entity.operazione;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.be.smartbank.model.entity.carta.Carta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PAGAMENTO")
public class Pagamento {

	@Id
	@Column(name = "ID_OPERAZIONE")
	private Long idOperazione;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId
	@JoinColumn(name = "ID_OPERAZIONE")
	private Operazione operazione;

	@Column(name = "ESERCENTE", nullable = false, length = 100)
	private String esercente;

	@Column(name = "ID_CARTA", insertable = false, updatable = false)
	private Integer idCarta;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CARTA", nullable = false)
	private Carta carta;
}
