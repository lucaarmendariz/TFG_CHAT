package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Produktu_Kategoria;

public interface Produktu_kategoria_service {
	public List<Produktu_Kategoria> getAll();
    
    public Optional<Produktu_Kategoria> find(Long id);
    
    public Produktu_Kategoria save(Produktu_Kategoria produktu_kategoria);
}
