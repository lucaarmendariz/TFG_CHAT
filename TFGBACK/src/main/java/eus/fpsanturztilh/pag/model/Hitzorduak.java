package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.*;
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
@Table(name = "hitzorduak")
@Schema(name = "Hitzorduak", description = "Entitate honek hitzorduak errepresentatzen du")
public class Hitzorduak implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Hitzorduen identifikatzaile bakarra (autogeneratua)", example = "1")
	private Long id;

	@Column(nullable = false)
	@Schema(description = "Hitzorduen eserleku kopurua", example = "6")
	private Integer eserlekua;

	@Column(nullable = false)
	@Schema(description = "Hitzorduen data", example = "2025-01-16")
	private LocalDate data;

	@Column(name = "hasiera_ordua", nullable = false)
	@Schema(description = "Hitzorduen esperotutako hasiera ordua", example = "10:00:00")
	private LocalTime hasieraOrdua;

	@Column(name = "amaiera_ordua", nullable = false)
	@Schema(description = "Hitzorduen esperotutako amaiera ordua", example = "12:00:00")
	private LocalTime amaieraOrdua;

	@Column(name = "hasiera_ordua_erreala")
	@Schema(description = "Hitzorduen hasiera ordua erreala", example = "10:04:14")
	private LocalTime hasieraOrduaErreala;

	@Column(name = "amaiera_ordua_erreala")
	@Schema(description = "Hitzorduen amaiera ordua erreala", example = "12:12:16")
	private LocalTime amaieraOrduaErreala;

	@Column(nullable = false, length = 100)
	@Schema(description = "Hitzorduen esleitutako bezero izena", example = "Pepe")
	private String izena;

	@Column(length = 9)
	@Schema(description = "Hitzorduen esleitutako bezero telefonoa", example = "609791083")
	private String telefonoa;

	@Column(length = 250)
	@Schema(description = "Hitzorduen esleitutako deskribapena", example = "Ilea moztea eta tintea ematea")
	private String deskribapena;

	@Column(nullable = false)
	@Schema(description = "Hitzorduen esleitutako bezeroa zentrokoa den edo ez", example = "K")
	private Character etxekoa;

	@OneToMany(mappedBy = "hitzordua")
	@JsonManagedReference
	@Schema(description = "Hitzorduen esleitutako ticketaren lerroak")
	private List<Ticket_lerroa> lerroak;

	@Column(name = "prezio_totala", precision = 10, scale = 2)
	@Schema(description = "Hitzorduen esleitutako prezio totala", example = "40.00")
	private BigDecimal prezioTotala;

	@OneToOne
	@JoinColumn(name = "id_langilea")
	@Schema(description = "Hitzorduen esleitutako langileak", example = "5")
	private Langileak langilea;

	@Column(name = "sortze_data", updatable = false)
	@Schema(description = "Hitzorduen sortze data (autogeneratua)")
	private LocalDateTime sortzeData = LocalDateTime.now();

	@Column(name = "eguneratze_data")
	@Schema(description = "Hitzorduen eguneratze data (autogeneratua)")
	private LocalDateTime eguneratzeData;

	@Column(name = "ezabatze_data")
	@Schema(description = "Hitzorduen ezabatze data (autogeneratua)")
	private LocalDateTime ezabatzeData;

}
