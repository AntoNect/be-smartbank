package com.sm.be.smartbank.model.entity.personale;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "UTENTE")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UTENTE")
	private Long idUtente;

	@Column(name = "EMAIL", nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "USERNAME", nullable = false, unique = true, length = 50)
	private String username;

	@Column(name = "PASSWORD", nullable = false, length = 255)
	private String password;

	@Column(name = "DATA_REGISTRAZIONE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataRegistrazione;

}
