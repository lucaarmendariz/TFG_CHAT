package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.Materialak;

public interface Materialak_repository extends JpaRepository<Materialak, Long> {
	public List<Materialak> findByIzena(String Izena);
}
