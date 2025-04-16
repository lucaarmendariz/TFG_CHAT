package eus.fpsanturztilh.pag.repository;

import eus.fpsanturtzilh.pag.dto.ServiciosPorCategoriaDTO;
import eus.fpsanturztilh.pag.model.Hitzorduak;
import jakarta.persistence.Tuple;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface Hitzordu_repository extends JpaRepository<Hitzorduak, Long> {
	public List<Hitzorduak> findByIzena(String Izena);
	
    List<Hitzorduak> findByData(LocalDate data);
		
	@Query(value = "SELECT " +
	        "l.id AS trabajador_id, " +
	        "l.izena AS trabajador, " +
	        "zc.izena AS categoria_servicio, " +
	        "COUNT(tl.id) AS cantidad_servicios " +
	        "FROM ticket_lerroak tl " +
	        "JOIN hitzorduak h ON tl.id_hitzordua = h.id " +
	        "JOIN langileak l ON h.id_langilea = l.id " +
	        "JOIN zerbitzuak z ON tl.id_zerbitzua = z.id " +
	        "JOIN zerbitzu_kategoria zc ON z.id_kategoria = zc.id " +
	        "WHERE tl.ezabatze_data IS NULL " +
	        "AND h.ezabatze_data IS NULL " +
	        "AND z.ezabatze_data IS NULL " +
	        "AND zc.ezabatze_data IS NULL " +
	        "AND zc.extra = 0 " +
	        "GROUP BY l.id, zc.id " +
	        "ORDER BY l.izena, zc.izena", nativeQuery = true)
	public List<Tuple> contarServiciosPorCategoria();
}
