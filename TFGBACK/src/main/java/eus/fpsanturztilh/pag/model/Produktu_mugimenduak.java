package eus.fpsanturztilh.pag.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "produktu_mugimenduak")
@Schema(name = "Produktu mugimenduak", description = "Entitate honek produktuen mugimenduak errepresentatzen du")
public class Produktu_mugimenduak implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Produktu mugimenduen identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_produktua", nullable = false)
	@Schema(description = "Produktu mugimenduen esleitutako produktuen id-ak", example = "1")
	private Produktuak produktu;

	@ManyToOne
	@JoinColumn(name = "id_langilea", nullable = false)
	@Schema(description = "Produktu mugimenduen esleitutako langilearen id-ak", example = "1")
	private Langileak langile;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@Column(nullable = false)
	@Schema(description = "Produktu mugimenduen data", example = "2025-01-15 09:16:31")
	private LocalDateTime data;

	@Column(nullable = false)
	@Schema(description = "Produktu mugimenduen kopurua", example = "1")
	private Integer kopurua;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Produktu mugimenduen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Produktu mugimenduen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Produktu mugimenduen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
