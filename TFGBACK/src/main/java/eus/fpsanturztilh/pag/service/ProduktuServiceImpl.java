package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Produktuak;
import eus.fpsanturztilh.pag.repository.Produktuak_repository;

@Service
public class ProduktuServiceImpl implements Produktu_service{

	@Autowired
	Produktuak_repository produktuakRepository; 
	
	@Override
	public List<Produktuak> getAll()
	{
		List<Produktuak> produktuList = produktuakRepository.findAll();
        return produktuList;
	}
	
	@Override
    public Optional<Produktuak> find(Long id)
    {
    	Optional<Produktuak> produktuList = produktuakRepository.findById(id);
    	return produktuList;
    }
	
	@Override
    public Produktuak save(Produktuak produktu)
    {
		return produktuakRepository.save(produktu);
    }
}
