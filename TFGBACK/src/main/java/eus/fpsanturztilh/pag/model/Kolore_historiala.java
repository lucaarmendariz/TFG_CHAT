package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "kolore_historialak")
@Schema(name = "Kolore historialak", description = "Entitate honek kolore historialak errepresentatzen du")
public class Kolore_historiala implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Kolore historialak identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_bezeroa", nullable = false)
	@JsonBackReference("historial")
	private Bezero_fitxa bezeroa;

	@Column(name = "id_produktua", nullable = false)
	private Long id_produktua;

	@Column(nullable = false)
	@Schema(description = "Kolore historialaren data", example = "2025-01-16")
	private LocalDate data;

	@Column(nullable = false)
	@Schema(description = "Kolore historialean erabili den kantitatea", example = "2")
	private double kantitatea;

	@Column(nullable = false)
	@Schema(description = "Kolore historialean erabili den bolumena", example = "2 ml")
	private String bolumena;

	@Column(nullable = true)
	@Schema(description = "Kolore historialaren oharrak", example = "Se ha teñido de rubio y luego a moreno")
	private String oharrak;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Kolore historialaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Kolore historialaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Kolore historialaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

}
