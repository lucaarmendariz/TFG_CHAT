package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Kolore_historiala_service {
	
	public List<Kolore_historiala> getAll();
    
    public Optional<Kolore_historiala> find(Long id);
    
    public Kolore_historiala save(Kolore_historiala kolore_historiala);
    
}
