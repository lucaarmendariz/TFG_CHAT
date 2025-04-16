package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Ordutegi_service {
	
	public List<Ordutegiak> getAll();
    
    public Optional<Ordutegiak> find(Long id_ordutegi);
    
    public Ordutegiak save(Ordutegiak ordutegi);
    
    public List<Ordutegiak> findByTaldea(Taldeak taldea);
    
}
