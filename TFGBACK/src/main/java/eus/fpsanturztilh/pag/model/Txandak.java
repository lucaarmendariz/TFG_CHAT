package eus.fpsanturztilh.pag.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "txandak")
@Schema(name = "Txandak", description = "Entitate honek txandak errepresentatzen du")
public class Txandak implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Txanden identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Txanden mota", example = "K")
	private String mota;

	@Column(name = "data", nullable = false)
	@Schema(description = "Txanden datak", example = "2025-01-15 09:16:31")
	private LocalDate data;

	@ManyToOne
	@JoinColumn(name = "id_langilea", nullable = false)
	@Schema(description = "Txanden esleitutako langilearen id-ak", example = "1")
	private Langileak langileak;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Txanden sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Txanden eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Txanden ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;
}
