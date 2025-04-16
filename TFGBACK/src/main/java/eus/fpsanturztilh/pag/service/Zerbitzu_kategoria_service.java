package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Zerbitzu_kategoria;
import eus.fpsanturztilh.pag.model.Zerbitzuak;

public interface Zerbitzu_kategoria_service {
	public List<Zerbitzu_kategoria> getAll();
    
    public Optional<Zerbitzu_kategoria> find(Long id);
    
    public Zerbitzu_kategoria save(Zerbitzu_kategoria zerbitzu_kategoria);
}
