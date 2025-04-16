package eus.fpsanturztilh.pag.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.repository.Erabiltzaile_repository;

public interface Erabiltzaile_service {
	    public Optional<Erabiltzaile> findByUsername(String username);
	    public boolean authenticate(String username, String rawPassword);
    
}
