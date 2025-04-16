package eus.fpsanturztilh.pag.service;

import java.util.*;
import eus.fpsanturztilh.pag.model.*;

public interface Material_mailegu_service {
	
	public List<Material_mailegua> getAll();
    
    public Optional<Material_mailegua> find(Long id);
    
    public Material_mailegua save(Material_mailegua material_mailegua);
    
	public void terminarMovimientos(List<Material_mailegua> movimientos);
    
    public void registrarMovimientos(List<Material_mailegua> movimientos);
    }
