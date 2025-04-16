package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "erabiltzaileak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Erabiltzailea", description = "Entitate honek erabiltzailea errepresentatzen du")
public class Erabiltzaile {

	@Id
	@Schema(description = "Erabiltzailearen izena", example = "Ikasle 1")
	private String username;

	@Schema(description = "Erabiltzailearen pasahitza", example = "pasahitzasegurua123")
	private String pasahitza;

	@Schema(description = "Erabiltzailearen rola", example = "ik")
	private String rola;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Erabiltzailearen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Erabiltzailearen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Erabiltzailearen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
