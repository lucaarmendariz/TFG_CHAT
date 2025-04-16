package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Materialak;
import eus.fpsanturztilh.pag.repository.Materialak_repository;

@Service
public class MaterialaServiceImpl implements Materiala_service{

	@Autowired
	Materialak_repository materialakRepository; 
	
	@Override
	public List<Materialak> getAll()
	{
		List<Materialak> materialList = materialakRepository.findAll();
        return materialList;
	}
	
	@Override
    public Optional<Materialak> find(Long id)
    {
    	Optional<Materialak> material_List = materialakRepository.findById(id);
    	return material_List;
    }
	
	@Override
    public Materialak create(Materialak materiala)
    {
		
		return materialakRepository.save(materiala);
    }
	
	@Override
    public Materialak save(Materialak materiala)
    {
		return materialakRepository.save(materiala);
    }
}
