package eus.fpsanturztilh.pag.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ticket_lerroak")
@Schema(name = "Ticket Lerroak", description = "Entitate honek ticket lerroak errepresentatzen du")
public class Ticket_lerroa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Ticket lerroen identifikatzaile bakarra (autogeneratua)", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_hitzordua")
    @JsonBackReference
	@Schema(description = "Ticket lerroari esleitutako hitzorduaren id-ak", example = "1")
    private Hitzorduak hitzordua;

    @OneToOne
    @JoinColumn(name = "id_zerbitzua")
	@Schema(description = "Ticket lerroari esleitutako zerbitzuak")
    private Zerbitzuak zerbitzuak;

    @Column(nullable = false)
	@Schema(description = "Ticket lerroen prezioa", example = "10.00")
    private double prezioa;

    @Column(name = "sortze_data", updatable = false)
	@Schema(description = "Ticket lerroen sortze data (autogeneratua)")
    private LocalDateTime sortzeData = LocalDateTime.now();

    @Column(name = "eguneratze_data")
	@Schema(description = "Ticket lerroen eguneratze data (autogeneratua)")
    private LocalDateTime eguneratzeData;

    @Column(name = "ezabatze_data")
	@Schema(description = "Ticket lerroen ezabatze data (autogeneratua)")
    private LocalDateTime ezabatzeData;

}

