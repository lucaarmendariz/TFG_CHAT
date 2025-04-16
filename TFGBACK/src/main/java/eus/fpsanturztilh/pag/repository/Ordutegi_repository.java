package eus.fpsanturztilh.pag.repository;

import eus.fpsanturztilh.pag.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface Ordutegi_repository extends JpaRepository<Ordutegiak, Long> {
    public List<Ordutegiak> findByTaldea(Taldeak taldea);
}