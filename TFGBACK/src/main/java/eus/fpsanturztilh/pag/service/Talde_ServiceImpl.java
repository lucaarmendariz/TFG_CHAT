package eus.fpsanturztilh.pag.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Talde_ServiceImpl implements Talde_service {
	@Autowired
	Talde_repository taldeRepository; 
	
	@Override
	public List<Taldeak> getAll()
	{
		List<Taldeak> taldeList = taldeRepository.findAll();
        return taldeList;
	}
	
	@Override
    public Optional<Taldeak> find(String kodea)
    {
    	Optional<Taldeak> talde_list = taldeRepository.findById(kodea);
    	return talde_list;
    }
	
	@Override
    public Taldeak save(Taldeak taldea)
    {
		return taldeRepository.save(taldea);
    }

	@Override
	public Taldeak create(Taldeak talde) {
		// TODO Auto-generated method stub
		return taldeRepository.save(talde);
	}
}
