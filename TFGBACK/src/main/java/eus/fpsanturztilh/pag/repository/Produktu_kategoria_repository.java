package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.*;

public interface Produktu_kategoria_repository extends JpaRepository<Produktu_Kategoria, Long> {
	public List<Produktuak> findByIzena(String Izena);
}
