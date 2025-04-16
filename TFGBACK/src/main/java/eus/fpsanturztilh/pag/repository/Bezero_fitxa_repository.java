package eus.fpsanturztilh.pag.repository;

import eus.fpsanturztilh.pag.model.Bezero_fitxa;

import org.springframework.data.jpa.repository.*;


// Bezero fitxa entiterako repositorioa. Zabaltzen du JpaRepository oinarrizko CRUD eragiketak emateko.
public interface Bezero_fitxa_repository extends JpaRepository<Bezero_fitxa, Long> {
}
