package eus.fpsanturztilh.pag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.fpsanturztilh.pag.model.Langileak;
import eus.fpsanturztilh.pag.model.Txandak;
import eus.fpsanturztilh.pag.repository.Txandak_repository;

@Service
public class Txandak_ServiceImpl implements Txandak_service {

    @Autowired
    Txandak_repository txandakRepository;

    @Override
    public List<Txandak> getAll() {
        return txandakRepository.findAll();
    }

    @Override
    public Optional<Txandak> find(Long id) {
        return txandakRepository.findById(id);
    }
    
    @Override
    public Txandak create(Txandak txandak) {
        return txandakRepository.save(txandak);
    }
    
    @Override
    public Txandak update(Txandak txandak) {
        if (txandakRepository.existsById(txandak.getId())) {
            return txandakRepository.save(txandak);
        } else {
            throw new RuntimeException("Txandak not found");
        }
    }
    
    @Override
    public List<Txandak> findByMota(String mota) {
        return txandakRepository.findByMota(mota);
    }
    
    @Override
    public List<Txandak> findByLangile(Langileak langileak) {
        return txandakRepository.findByLangileak(langileak);
    }
}
