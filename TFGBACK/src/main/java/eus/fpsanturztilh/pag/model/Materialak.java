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
@Table(name = "materialak")
@Schema(name = "Materialak", description = "Entitate honek materialak errepresentatzen du")
public class Materialak implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Materialaren identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Materialaren etiketa", example = "tij-01")
	private String etiketa;

	@Column(nullable = false)
	@Schema(description = "Materialaren izena", example = "tijera 1")
	private String izena;

	@ManyToOne
	@JoinColumn(name = "id_kategoria", nullable = false)
	@JsonBackReference("mat-kat")
	@Schema(description = "Materialean esleitutako kategoriaren id", example = "1")
	private Material_kategoria materialKategoria;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Materialaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Materialaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Materialaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

	public Long getKategoriaId() {
		return materialKategoria != null ? materialKategoria.getId() : null;
	}
}
