package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.Zerbitzuak;

public interface Zerbitzu_repository extends JpaRepository<Zerbitzuak, Long> {
	public List<Zerbitzuak> findByIzena(String Izena);
}

