package eus.fpsanturztilh.pag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.Langileak;
import eus.fpsanturztilh.pag.model.Txandak;

public interface Txandak_repository extends JpaRepository<Txandak, Long> {
	public List<Txandak> findByMota(String mota);
	public List<Txandak> findByLangileak(Langileak langileak);

}