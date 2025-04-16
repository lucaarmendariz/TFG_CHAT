package eus.fpsanturztilh.pag.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Kolore_historiala_ServiceImpl implements Kolore_historiala_service {
	@Autowired
	Kolore_historiala_repository kolore_historialaRepository; 
	
	@Override
	public List<Kolore_historiala> getAll()
	{
		List<Kolore_historiala> kolore_historiala_List = kolore_historialaRepository.findAll();
        return kolore_historiala_List;
	}
	
	@Override
    public Optional<Kolore_historiala> find(Long id)
    {
    	Optional<Kolore_historiala> kolore_historiala_List = kolore_historialaRepository.findById(id);
    	return kolore_historiala_List;
    }
	
	@Override
    public Kolore_historiala save(Kolore_historiala kolore_hist)
    {
		return kolore_historialaRepository.save(kolore_hist);
    }
}
