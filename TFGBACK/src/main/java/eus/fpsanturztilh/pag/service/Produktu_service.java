package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Produktuak;

public interface Produktu_service {
	public List<Produktuak> getAll();
    
    public Optional<Produktuak> find(Long id);
    
    public Produktuak save(Produktuak produktu);    
}
