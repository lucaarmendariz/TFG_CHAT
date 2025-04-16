package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Talde_service {
	
	public List<Taldeak> getAll();
    
    public Optional<Taldeak> find(String kodea);
    
    public Taldeak create(Taldeak talde);

	public Taldeak save(Taldeak talde);
}
