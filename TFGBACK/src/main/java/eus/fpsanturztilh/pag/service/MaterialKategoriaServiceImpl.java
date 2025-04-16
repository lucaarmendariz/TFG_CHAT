package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Material_kategoria;
import eus.fpsanturztilh.pag.repository.Material_kategoria_repository;

@Service
public class MaterialKategoriaServiceImpl implements Material_kategoria_service{

	@Autowired
	Material_kategoria_repository materialakKategoriaRepository; 
	
	@Override
	public List<Material_kategoria> getAll()
	{
		List<Material_kategoria> materialKatList = materialakKategoriaRepository.findAll();
        return materialKatList;
	}
	
	@Override
    public Optional<Material_kategoria> find(Long id)
    {
    	Optional<Material_kategoria> materialaKatList = materialakKategoriaRepository.findById(id);
    	return materialaKatList;
    }
	
	@Override
    public Material_kategoria save(Material_kategoria material_kategoria)
    {
		
		return materialakKategoriaRepository.save(material_kategoria);
    }
}
