package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Zerbitzuak;
import eus.fpsanturztilh.pag.repository.Zerbitzu_repository;

@Service
public class ZerbitzuServiceImpl implements Zerbitzu_service{

	@Autowired
	Zerbitzu_repository zerbitzuakRepository; 
	
	@Override
	public List<Zerbitzuak> getAll()
	{
		List<Zerbitzuak> zerbitzuList = zerbitzuakRepository.findAll();
        return zerbitzuList;
	}
	
	@Override
    public Optional<Zerbitzuak> find(Long id)
    {
    	Optional<Zerbitzuak> zerbitzuList = zerbitzuakRepository.findById(id);
    	return zerbitzuList;
    }
	
	@Override
    public Zerbitzuak save(Zerbitzuak zerbitzu)
    {
		
		return zerbitzuakRepository.save(zerbitzu);
    }
}
