package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Zerbitzuak;

public interface Zerbitzu_service {
	public List<Zerbitzuak> getAll();
    
    public Optional<Zerbitzuak> find(Long id);
    
    public Zerbitzuak save(Zerbitzuak zerbitzu);
}
