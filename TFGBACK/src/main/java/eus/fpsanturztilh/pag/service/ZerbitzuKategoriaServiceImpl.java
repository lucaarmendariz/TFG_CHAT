package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class ZerbitzuKategoriaServiceImpl implements Zerbitzu_kategoria_service{

	@Autowired
	Zerbitzu_kategoria_repository zerbitzuakKategoriaRepository; 
	
	@Override
	public List<Zerbitzu_kategoria> getAll()
	{
		List<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuakKategoriaRepository.findAll();
        return zerbitzuKategoriaList;
	}
	
	@Override
    public Optional<Zerbitzu_kategoria> find(Long id)
    {
    	Optional<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuakKategoriaRepository.findById(id);
    	return zerbitzuKategoriaList;
    }
	
	@Override
    public Zerbitzu_kategoria save(Zerbitzu_kategoria zerbitzu_kategoria)
    {
		return zerbitzuakKategoriaRepository.save(zerbitzu_kategoria);
    }
}
