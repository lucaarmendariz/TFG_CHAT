package eus.fpsanturztilh.pag.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Ordutegi_ServiceImpl implements Ordutegi_service {
	
	
	@Autowired
	Ordutegi_repository ordutegiRepository; 
	
	@Override
	public List<Ordutegiak> getAll()
	{
		List<Ordutegiak> langileList = ordutegiRepository.findAll();
        return langileList;
	}
	
	@Override
    public Optional<Ordutegiak> find(Long id)
    {
    	Optional<Ordutegiak> langile_list = ordutegiRepository.findById(id);
    	return langile_list;
    }
	
	@Override
    public Ordutegiak save(Ordutegiak ordutegi)
    {
		return ordutegiRepository.save(ordutegi);
    }
	
	@Override
	public List<Ordutegiak> findByTaldea(Taldeak taldea) {
        return ordutegiRepository.findByTaldea(taldea);
    }
}
