package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Produktu_mugimendua_service {
	
	public List<Produktu_mugimenduak> getAll();
    
    public Optional<Produktu_mugimenduak> find(Long id);
    
    public Produktu_mugimenduak create(Produktu_mugimenduak mugimendua);
    
    public void registrarMovimientos(List<Produktu_mugimenduak> movimientos);
    
}
