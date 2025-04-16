package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Langile_service {
	
	public List<Langileak> getAll();
    
    public Optional<Langileak> find(Long id_langile);
    
    public Langileak create(Langileak langile);

	public Langileak save(Langileak langile);
    
}
