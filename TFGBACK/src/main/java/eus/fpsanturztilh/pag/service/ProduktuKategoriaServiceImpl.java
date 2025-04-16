package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Produktu_Kategoria;
import eus.fpsanturztilh.pag.repository.Produktu_kategoria_repository;

@Service
public class ProduktuKategoriaServiceImpl implements Produktu_kategoria_service{

	@Autowired
	Produktu_kategoria_repository produktuakKategoriaRepository; 
	
	@Override
	public List<Produktu_Kategoria> getAll()
	{
		List<Produktu_Kategoria> produktuKatList = produktuakKategoriaRepository.findAll();
        return produktuKatList;
	}
	
	@Override
    public Optional<Produktu_Kategoria> find(Long id)
    {
    	Optional<Produktu_Kategoria> produktuKatList = produktuakKategoriaRepository.findById(id);
    	return produktuKatList;
    }
	
	@Override
    public Produktu_Kategoria save(Produktu_Kategoria produktu_kategoria)
    {
		
		return produktuakKategoriaRepository.save(produktu_kategoria);
    }
}
