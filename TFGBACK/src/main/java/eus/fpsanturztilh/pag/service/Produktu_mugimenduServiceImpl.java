package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Langileak;
import eus.fpsanturztilh.pag.model.Produktu_mugimenduak;
import eus.fpsanturztilh.pag.model.Produktuak;
import eus.fpsanturztilh.pag.repository.Langile_repository;
import eus.fpsanturztilh.pag.repository.Produktu_mugimenduak_repository;
import eus.fpsanturztilh.pag.repository.Produktuak_repository;

@Service
public class Produktu_mugimenduServiceImpl implements Produktu_mugimendua_service{

	@Autowired
	Produktu_mugimenduak_repository mugimenduakRepository; 
	
	@Autowired
	Produktuak_repository produktoRepository; 
	
	@Autowired
	Langile_repository langileRepository;
	
	@Override
	public List<Produktu_mugimenduak> getAll()
	{
		List<Produktu_mugimenduak> mugimenduList = mugimenduakRepository.findAll();
        return mugimenduList;
	}
	
	@Override
    public Optional<Produktu_mugimenduak> find(Long id)
    {
    	Optional<Produktu_mugimenduak> mugimenduList = mugimenduakRepository.findById(id);
    	return mugimenduList;
    }
	
	@Override
    public Produktu_mugimenduak create(Produktu_mugimenduak mugimenduak)
    {
		
		return mugimenduakRepository.save(mugimenduak);
    }
	
	@Override
	public void registrarMovimientos(List<Produktu_mugimenduak> movimientos) {
        for (Produktu_mugimenduak movimiento : movimientos) {
        	Optional<Produktuak> produkto = produktoRepository.findById(movimiento.getProduktu().getId());
        	if(produkto.isPresent()) {
        		Produktuak productoExistente = produkto.get();
        		Integer nuevoStock = productoExistente.getStock() - movimiento.getKopurua();
        		productoExistente.setStock(nuevoStock);
                produktoRepository.save(productoExistente);
                movimiento.setProduktu(productoExistente);
        	}
        	Optional<Langileak> langile = langileRepository.findById(movimiento.getLangile().getId());
        	if(langile.isPresent()) {
        		Langileak langileExistente = langile.get();
                movimiento.setLangile(langileExistente);
        	}
        	mugimenduakRepository.save(movimiento);
        }
    }
}
