package eus.fpsanturztilh.pag.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Ticket_lerro_ServiceImpl implements Ticket_lerro_service {
	@Autowired
	Ticket_lerro_repository ticket_lerroRepository; 
	
	@Override
	public List<Ticket_lerroa> getAll()
	{
		List<Ticket_lerroa> ticket_lerro_list = ticket_lerroRepository.findAll();
        return ticket_lerro_list;
	}
	
	@Override
    public Optional<Ticket_lerroa> find(Long id)
    {
    	Optional<Ticket_lerroa> ticket_lerro_list = ticket_lerroRepository.findById(id);
    	return ticket_lerro_list;
    }
	
	@Override
    public Ticket_lerroa save(Ticket_lerroa ticket_lerro)
    {
		return ticket_lerroRepository.save(ticket_lerro);
    }
}
