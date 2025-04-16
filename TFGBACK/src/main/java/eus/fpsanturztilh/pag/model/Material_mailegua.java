package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "material_maileguaK")
@Schema(name = "Material Mailegua", description = "Entitate honek materialaren maileguak errepresentatzen du")
public class Material_mailegua implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Material maileguaren identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_materiala", nullable = false)
	@Schema(description = "Material maileguen esleitutako materialaren id-ak", example = "1")
	private Materialak materiala;

	@OneToOne
	@JoinColumn(name = "id_langilea", nullable = false)
	@Schema(description = "Material maileguen esleitutako langilearen id-ak", example = "1")
	private Langileak langilea;

	@Column(name = "hasiera_data", nullable = false)
	@Schema(description = "Material maileguen hasiera data (autogeneratua)")
	private LocalDateTime hasieraData;

	@Column(name = "amaiera_data")
	@Schema(description = "Material maileguen amaiera data (autogeneratua)")
	private LocalDateTime amaieraData;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Material maileguen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Material maileguen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Material maileguen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

}
