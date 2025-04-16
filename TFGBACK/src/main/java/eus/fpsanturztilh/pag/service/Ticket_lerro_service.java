package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Ticket_lerro_service {
	
	public List<Ticket_lerroa> getAll();
    
    public Optional<Ticket_lerroa> find(Long id);
    
    public Ticket_lerroa save(Ticket_lerroa ticket_lerro);
    
}
