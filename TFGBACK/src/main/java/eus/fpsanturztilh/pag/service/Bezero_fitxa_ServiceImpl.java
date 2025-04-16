package eus.fpsanturztilh.pag.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Bezero_fitxa_ServiceImpl implements Bezero_fitxa_service{
	
	@Autowired
	Bezero_fitxa_repository bezeroFitxaRepository; 
	
	@Override
	public List<Bezero_fitxa> getAll()
	{
		// Metodo honekin lortzen dugu bezero fitxa guztiak lortzea
		List<Bezero_fitxa> bezero_fitxa_list = bezeroFitxaRepository.findAll();
        return bezero_fitxa_list;
	}
	
	@Override
    public Optional<Bezero_fitxa> find(Long id)
    {
    	// Metodo honekin bilatzen dugu bezero fitxa espezifiko bat id-tik bilatuz
    	Optional<Bezero_fitxa> bezero_fitxa_list = bezeroFitxaRepository.findById(id);
    	return bezero_fitxa_list;
    }
	
	@Override
    public Bezero_fitxa save(Bezero_fitxa bezero_fitxa)
    {
		// Honekin aldatutako aldaketak gordetzen dugu bezero fitxan
		return bezeroFitxaRepository.save(bezero_fitxa);
    }
}
