package eus.fpsanturztilh.pag.repository;

import eus.fpsanturztilh.pag.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface Talde_repository extends JpaRepository<Taldeak, String> {
	public List<Taldeak> findByKodea(String kodea);
}
