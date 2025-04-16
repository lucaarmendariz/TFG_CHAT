package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.*;

public interface Material_kategoria_repository extends JpaRepository<Material_kategoria, Long> {
	public List<Materialak> findByIzena(String Izena);
}
