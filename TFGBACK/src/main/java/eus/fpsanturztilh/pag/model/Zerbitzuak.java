package eus.fpsanturztilh.pag.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "zerbitzuak")
@Schema(name = "Zerbitzuak", description = "Entitate honek zerbitzuak errepresentatzen du")
public class Zerbitzuak implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Zerbitzuen identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Zerbitzuen izena", example = "corte largo")
	private String izena;

	@Column(name = "etxeko_prezioa", nullable = false)
	@Schema(description = "Zerbitzuen etxeko prezioa", example = "5.00")
	private Double etxekoPrezioa;

	@Column(name = "kanpoko_prezioa", nullable = false)
	@Schema(description = "Zerbitzuen kanpoko prezioa", example = "10.00")
	private Double kanpokoPrezioa;

	@ManyToOne
	@JoinColumn(name = "id_kategoria", nullable = false)
	@JsonBackReference
	@Schema(description = "Zerbitzuari esleitutako kategoriaren id-ak", example = "1")
	private Zerbitzu_kategoria zerbitzuKategoria;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Zerbitzuen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Zerbitzuen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Zerbitzuen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
