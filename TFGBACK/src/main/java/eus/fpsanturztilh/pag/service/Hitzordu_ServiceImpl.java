package eus.fpsanturztilh.pag.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturtzilh.pag.dto.ServiciosPorCategoriaDTO;
import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;
import jakarta.persistence.Tuple;

@Service
public class Hitzordu_ServiceImpl implements Hitzordu_service {
	@Autowired
	Hitzordu_repository hitzorduRepository; 
	
	@Override
	public List<Hitzorduak> getAll()
	{
		List<Hitzorduak> hitzorduakList = hitzorduRepository.findAll();
        return hitzorduakList;
	}
	
	@Override
	public List<Hitzorduak> getByDate(LocalDate date) {
        return hitzorduRepository.findByData(date);
    }
	
	@Override
    public Optional<Hitzorduak> find(Long id)
    {
    	Optional<Hitzorduak> hitzordua_list = hitzorduRepository.findById(id);
    	return hitzordua_list;
    }
	
	@Override
    public Hitzorduak save(Hitzorduak hitzordu)
    {
		return hitzorduRepository.save(hitzordu);
    }
	
	public Map<Long, Map<String, Object>> obtenerServiciosPorCategoriaAgrupado() {
	    List<Tuple> results = hitzorduRepository.contarServiciosPorCategoria();
	    
	    Map<Long, Map<String, Object>> datosTrabajadores = new HashMap<>();
	    
	    for (Tuple tuple : results) {
	    	Number langileIdNumber = tuple.get("trabajador_id", Number.class);
	    	Long langileId = langileIdNumber.longValue(); // Convierte a Long correctamente
	        String trabajador = tuple.get("trabajador", String.class); // Nombre del trabajador
	        String categoriaServicio = tuple.get("categoria_servicio", String.class);
	        Long cantidadServicios = tuple.get("cantidad_servicios", Long.class);

	        // Si el ID del trabajador aún no existe en el mapa, lo agregamos
	        if (!datosTrabajadores.containsKey(langileId)) {
	            Map<String, Object> trabajadorData = new HashMap<>();
	            trabajadorData.put("nombre", trabajador);
	            trabajadorData.put("servicios", new HashMap<String, Long>());
	            datosTrabajadores.put(langileId, trabajadorData);
	        }

	        // Agregar la categoría de servicio y cantidad dentro del mapa de servicios
	        Map<String, Long> servicios = (Map<String, Long>) datosTrabajadores.get(langileId).get("servicios");
	        servicios.put(categoriaServicio, cantidadServicios);
	    }

	    return datosTrabajadores;
	}


}
