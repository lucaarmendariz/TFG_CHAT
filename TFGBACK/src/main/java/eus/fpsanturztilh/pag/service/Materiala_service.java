package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Materialak;

public interface Materiala_service {
	public List<Materialak> getAll();
    
    public Optional<Materialak> find(Long id);
    
    public Materialak create(Materialak material);
    
    public Materialak save(Materialak materiala);
}
