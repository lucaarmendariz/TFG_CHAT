package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "bezero_fitxak")
@Schema(name = "Bezero Fitxa", description = "Entitate honek bezero fitxa errepresentatzen du")
public class Bezero_fitxa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Bezero fitxaren identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Bezeroen fitxaren izena", example = "Pepe")
	private String izena;

	@Column(nullable = false)
	@Schema(description = "Bezeroen fitxaren abizena", example = "Ruiz")
	private String abizena;

	@Column(nullable = false)
	@Schema(description = "Fitxaren esleitutako bezero telefonoa", example = "609791083")
	private String telefonoa;

	@Column(name = "azal_sentikorra", nullable = false)
	@Schema(description = "Bezeroaren azala sentikorra den balioa", example = "B")
	private String azalSentikorra;

	@OneToMany(mappedBy = "bezeroa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference("historial")
	@Schema(description = "Bezeroaren tintearen kolore historiala gordetzen duen lista")
	private List<Kolore_historiala> historiala;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Bezeroaren fitxaren sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Bezeroaren fitxaren eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Bezeroaren fitxaren ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

}
