package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.*;
import java.util.List;
import com.fasterxml.jackson.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "taldeak")
@Schema(name = "Taldeak", description = "Entitate honek taldeak errepresentatzen du")
public class Taldeak implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Schema(description = "Taldearen identifikatzailea", example = "3PAG2")
	private String kodea;

	@Column(nullable = false)
	@Schema(description = "Taldearen izena", example = "Multimedia")
	private String izena;

	@OneToMany(mappedBy = "taldea", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("taldeak-langileak")
	@Schema(description = "Taldeari esleitutako langileen zerrenda")
	private List<Langileak> langileak;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Taldearen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Taldearen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Taldearen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

}
