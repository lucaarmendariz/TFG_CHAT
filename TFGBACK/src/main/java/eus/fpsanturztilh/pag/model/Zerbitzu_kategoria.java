package eus.fpsanturztilh.pag.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "zerbitzu_kategoria")
@Schema(name = "Zerbitzu Kategoriak", description = "Entitate honek zerbitzuen kategoriak errepresentatzen du")
public class Zerbitzu_kategoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Zerbitzu kategorien identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Zerbitzu kategorien izena", example = "corte")
	private String izena;

	@Column(name = "kolorea", nullable = false)
	@Schema(description = "Zerbitzu kategorien kolorea", example = "1")
	private boolean kolorea;

	@Column(name = "extra", nullable = false)
	@Schema(description = "Zerbitzu kategorien extrak", example = "1")
	private boolean extra;

	@OneToMany(mappedBy = "zerbitzuKategoria", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Schema(description = "Zerbitzu kategoriei esleitutako zerbitzuen zerrenda")
	private List<Zerbitzuak> zerbitzuak;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Zerbitzu kategorien sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Zerbitzu kategorien eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Zerbitzu kategorien ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
