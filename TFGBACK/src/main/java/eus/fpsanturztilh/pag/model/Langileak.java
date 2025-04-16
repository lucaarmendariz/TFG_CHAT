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
@Table(name = "langileak")
@Schema(name = "Langileak", description = "Entitate honek langileak errepresentatzen du")
public class Langileak implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Langileak identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Langileen izena", example = "Luca")
	private String izena;

	@Column(nullable = false)
	@Schema(description = "Langileen abizena", example = "Armendariz")
	private String abizenak;

	@ManyToOne
	@JoinColumn(name = "kodea", nullable = false)
	@JsonBackReference("taldeak-langileak")
	@Schema(description = "Langileen esleitutako taldea")
	private Taldeak taldea;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Langileen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Langileen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Langileen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

	// Taldearen kodea erakusteko metodo bat eranstea
	public String getTaldeKodea() {
		return taldea != null ? taldea.getKodea() : null;
	}

	public String getTaldeIzena() {
		return taldea != null ? taldea.getIzena() : null;
	}
	// Aukeran, DTO bat sor dezakezu taldearen datuen zati bat bakarrik azaldu nahi
	// baduzu
}
