package eus.fpsanturztilh.pag.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.*;

@Service
public class Material_mailegu_ServiceImpl implements Material_mailegu_service {
	@Autowired
	Material_mailegua_repository maileguaRepository; 
	
	@Autowired
	Materialak_repository materialRepository;
	
	@Autowired
	Langile_repository langileRepository;
	
	@Override
	public List<Material_mailegua> getAll()
	{
		List<Material_mailegua> mailegua_list = maileguaRepository.findAll();
        return mailegua_list;
	}
	
	@Override
    public Optional<Material_mailegua> find(Long id)
    {
    	Optional<Material_mailegua> mailegua_list = maileguaRepository.findById(id);
    	return mailegua_list;
    }
	
	@Override
    public Material_mailegua save(Material_mailegua material_mailegua)
    {
		return maileguaRepository.save(material_mailegua);
    }
	
	
	@Override
	public void terminarMovimientos(List<Material_mailegua> movimientos) {
        for (Material_mailegua movimiento : movimientos) {
        	Optional<Material_mailegua> material_mailegu = maileguaRepository.findById(movimiento.getId());
        	if(material_mailegu.isPresent()) {
        		Material_mailegua maileguak = material_mailegu.get();
        		maileguak.setAmaieraData(LocalDateTime.now());
            	maileguaRepository.save(maileguak);
        	}
        }
    }
	
	@Override
	public void registrarMovimientos(List<Material_mailegua> movimientos) {
        for (Material_mailegua movimiento : movimientos) {
        	Optional<Materialak> material = materialRepository.findById(movimiento.getMateriala().getId());
        	if(material.isPresent()) {
        		Materialak materialExistente = material.get();
        		materialRepository.save(materialExistente);
        		movimiento.setMateriala(materialExistente);
        	}
        	Optional<Langileak> langile = langileRepository.findById(movimiento.getLangilea().getId());
        	if(langile.isPresent()) {
        		Langileak langileExistente = langile.get();
                movimiento.setLangilea(langileExistente);
        	}
        	movimiento.setHasieraData(LocalDateTime.now());
        	maileguaRepository.save(movimiento);
        }
    }
}
