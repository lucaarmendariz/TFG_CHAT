package eus.fpsanturztilh.pag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.fpsanturztilh.pag.model.*;


public interface Erabiltzaile_repository extends JpaRepository<Erabiltzaile, String> {
    Optional<Erabiltzaile> findByUsername(String username);
}
