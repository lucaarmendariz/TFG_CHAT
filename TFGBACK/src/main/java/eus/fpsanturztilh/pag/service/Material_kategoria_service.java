package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import eus.fpsanturztilh.pag.model.Material_kategoria;

public interface Material_kategoria_service {
	public List<Material_kategoria> getAll();
    
    public Optional<Material_kategoria> find(Long id);
    
    public Material_kategoria save(Material_kategoria material_kategoria);
}
