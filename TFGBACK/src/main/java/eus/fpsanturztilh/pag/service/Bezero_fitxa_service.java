package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

// Hemen sortzen dugu interfazea, metodoak erabiltzeko service erabiliz
public interface Bezero_fitxa_service {
	
	public List<Bezero_fitxa> getAll();
    
    public Optional<Bezero_fitxa> find(Long id);
    
    public Bezero_fitxa save(Bezero_fitxa bezero_fitxa);
    
}
