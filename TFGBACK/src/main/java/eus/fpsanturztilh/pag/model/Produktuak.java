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
@Table(name = "produktuak")
@Schema(name = "Produktuak", description = "Entitate honek produktuak errepresentatzen du")
public class Produktuak implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Produktuaren identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Produktuaren izena", example = "tinte 1")
	private String izena;

	@Column
	@Schema(description = "Produktuaren deskribapena", example = "tinte de pelo rubio de intensidad alta")
	private String deskribapena;

	@ManyToOne
	@JoinColumn(name = "id_kategoria", nullable = false)
	@JsonBackReference("kategoria")
	@Schema(description = "Produktuaren esleitutako kategoriaren id", example = "1")
	private Produktu_Kategoria produktuKategoria;

	@Column(nullable = false)
	@Schema(description = "Produktuaren marka", example = "pantene")
	private String marka;

	@Column(nullable = false)
	@Schema(description = "Produktuaren stock", example = "20")
	private Integer stock;

	@Column(name = "stock_alerta", nullable = false)
	@Schema(description = "Produktuaren stock alerta", example = "3")
	private Integer stockAlerta;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Produktuaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Produktuaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Produktuaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
