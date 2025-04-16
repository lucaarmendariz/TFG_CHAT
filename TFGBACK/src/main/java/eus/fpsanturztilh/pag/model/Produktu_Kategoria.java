package eus.fpsanturztilh.pag.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "produktu_kategoria")
@Schema(name = "Produktu Kategoriak", description = "Entitate honek produktu kategoriak errepresentatzen du")
public class Produktu_Kategoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Produktu kategorien identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Produktu kategoriaren izena", example = "Tintes")
	private String izena;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Produktu kategoriaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Produktu kategoriaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Produktu kategoriaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

	@OneToMany(mappedBy = "produktuKategoria", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("kategoria")
	@Schema(description = "Produktu kategoriaren esleitutako produktu zerrenda")
	private List<Produktuak> produktuak;
}
