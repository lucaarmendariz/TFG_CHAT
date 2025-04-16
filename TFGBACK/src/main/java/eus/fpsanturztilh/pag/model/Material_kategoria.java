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
@Table(name = "material_kategoria")
@Schema(name = "Material Kategoria", description = "Entitate honek materialaren kategoriak errepresentatzen du")
public class Material_kategoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Material kategoriaren identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Material kategoriaren izena", example = "Tijeras")
	private String izena;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Material kategoriaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Material kategoriaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Material kategoriaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

	@OneToMany(mappedBy = "materialKategoria", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("mat-kat")
	@Schema(description = "Material kategoriaren esleitutako material zerrenda")
	private List<Materialak> materialak;
}
