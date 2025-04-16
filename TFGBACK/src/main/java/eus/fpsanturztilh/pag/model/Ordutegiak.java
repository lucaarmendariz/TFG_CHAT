package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ordutegiak")
@Schema(name = "Ordutegiak", description = "Entitate honek ordutegiak errepresentatzen du")
public class Ordutegiak implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Ordutegien identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "kodea", nullable = false)
	@Schema(description = "Ordutegietan esleitutako taldearen kodea", example = "1")
	private Taldeak taldea;

	@Column(nullable = false)
	@Schema(description = "Ordutegien egunaren zenbakia", example = "1")
	private Long eguna;

	@Column(name = "hasiera_data", nullable = false)
	@Schema(description = "Ordutegien hasiera data", example = "2025-01-01")
	private LocalDate hasieraData;

	@Column(name = "amaiera_data", nullable = false)
	@Schema(description = "Ordutegien amaiera data", example = "2025-06-01")
	private LocalDate amaieraData;

	@Column(name = "hasiera_ordua", nullable = false)
	@Schema(description = "Ordutegien hasierako ordua", example = "08:00:00")
	private LocalTime hasieraOrdua;

	@Column(name = "amaiera_ordua", nullable = false)
	@Schema(description = "Ordutegien amaiera ordua", example = "15:00:00")
	private LocalTime amaieraOrdua;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Ordutegien sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Ordutegien eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Ordutegien ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

	public boolean hasInvalidFields() {
		return taldea == null || eguna == null || eguna < 1 || eguna > 7 || // Suponiendo que representa días de la
																			// semana (1-7)
				hasieraData == null || amaieraData == null || hasieraData.isAfter(amaieraData) || // La fecha de inicio
																									// no puede ser
																									// posterior a la de
																									// finalización
				hasieraOrdua == null || amaieraOrdua == null || hasieraOrdua.isAfter(amaieraOrdua); // La hora de inicio
																									// no puede ser
																									// posterior a la de
																									// finalización
	}

}
