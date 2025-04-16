package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.Produktuak;

public interface Produktuak_repository extends JpaRepository<Produktuak, Long> {
	public List<Produktuak> findByIzena(String Izena);
}
