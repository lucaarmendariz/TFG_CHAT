package eus.fpsanturztilh.pag.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Langile_ServiceImpl implements Langile_service {
	@Autowired
	Langile_repository langileRepository; 
	
	@Override
	public List<Langileak> getAll()
	{
		List<Langileak> langileList = langileRepository.findAll();
        return langileList;
	}
	
	@Override
    public Optional<Langileak> find(Long id)
    {
    	Optional<Langileak> langile_list = langileRepository.findById(id);
    	return langile_list;
    }
	
	@Override
    public Langileak save(Langileak langile)
    {
		return langileRepository.save(langile);
    }

	@Override
	public Langileak create(Langileak langile) {
		// TODO Auto-generated method stub
		return null;
	}
}
