package eus.fpsanturztilh.pag.repository;

import eus.fpsanturztilh.pag.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface Langile_repository extends JpaRepository<Langileak, Long> {
	public List<Langileak> findByIzena(String Izena);
}
