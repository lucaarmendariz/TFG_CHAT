package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/taldeak")
@Tag(name = "Taldeak", description = "Taldeak kudeatzeko kontroladorea")
public class Talde_controller {

	@Autowired
	Talde_ServiceImpl taldeService;

	@Autowired
	Ordutegi_ServiceImpl ordutegiService;

	@GetMapping("")
	@Operation(summary = "Lortu talde guztiak", description = "Talde guztien zerrenda itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Taldeen zerrenda eskuratu da") })
	public ResponseEntity<List<Taldeak>> getAllTaldeak() {

		List<Taldeak> taldeList = taldeService.getAll()
			.stream()
	        .filter(talde -> talde.getEzabatzeData() == null)
	        .map(talde -> {
	        	talde.setLangileak(talde.getLangileak()
	                    .stream()
	                    .filter(langile -> langile.getEzabatzeData() == null)
	                    .collect(Collectors.toList()));
	            return talde;
	        })
	        .collect(Collectors.toList());
		return ResponseEntity.ok(taldeList);
	}

	@GetMapping("/kodea/{kodea}")
	@Operation(summary = "Bilatu taldea kodearen arabera", description = "Kodea erabiliz taldea bilatzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Taldea aurkitu da"),
			@ApiResponse(responseCode = "404", description = "Taldea ez da aurkitu") })
	public ResponseEntity<Taldeak> findTaldeak(@PathVariable String kodea) {
		Optional<Taldeak> talde_list = taldeService.find(kodea);
		if (talde_list.isPresent()) {
			return ResponseEntity.ok(talde_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Sortu taldea", description = "Kodea duen taldea sortzen du edo lehendik ezabatutako bat berreskuratzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Taldea ongi sortu da"),
			@ApiResponse(responseCode = "400", description = "Eskaera baliogabea") })
	public ResponseEntity<Taldeak> createTaldeak(@RequestBody Taldeak talde) {
		// Verificar si existe un Talde con el mismo 'kodea' y que esté marcado como
		// eliminado
		Optional<Taldeak> existingTalde = taldeService.find(talde.getKodea());

		if (existingTalde.isPresent() && existingTalde.get().getEzabatzeData() != null) {
			// Si existe y tiene 'ezabatzeData' (borrado), restauramos el Talde
			Taldeak restoredTalde = existingTalde.get();
			restoredTalde.setEzabatzeData(null); // Restauramos el Talde eliminando la 'ezabatzeData'
			restoredTalde.setIzena(talde.getIzena()); // Actualizamos el nombre
			restoredTalde.setEguneratzeData(LocalDateTime.now()); // Actualizamos la fecha de actualización

			// Guardamos el Talde restaurado
			Taldeak updatedTalde = taldeService.save(restoredTalde);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedTalde);
		} else {
			// Si no existe tal Talde o no está marcado como borrado, creamos uno nuevo
			Taldeak newTalde = taldeService.create(talde);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTalde);
		}
	}

	@PutMapping("/{kodea}")
	@Operation(summary = "Eguneratu taldea", description = "Kodea erabiliz taldea eguneratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Taldea eguneratu da"),
			@ApiResponse(responseCode = "404", description = "Taldea ez da aurkitu") })
	public ResponseEntity<Taldeak> saveTaldea(@PathVariable String kodea, @RequestBody Taldeak taldeUpdated) {
		Optional<Taldeak> talde_list = taldeService.find(kodea);
		if (talde_list.isPresent()) {
			Taldeak existingTaldea = talde_list.get();

			existingTaldea.setIzena(taldeUpdated.getIzena());
			existingTaldea.setKodea(taldeUpdated.getKodea());
			existingTaldea.setEguneratzeData(LocalDateTime.now());

			taldeService.save(existingTaldea);
			return ResponseEntity.status(HttpStatus.OK).body(existingTaldea); // Devolver el objeto actualizado
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/kodea/{kodea}")
	@Operation(summary = "Ezabatu taldea", description = "Kodea erabiliz taldea ezabatzen du, baita bere ordutegiak ere.", responses = {
			@ApiResponse(responseCode = "200", description = "Taldea ongi ezabatu da"),
			@ApiResponse(responseCode = "404", description = "Taldea ez da aurkitu") })
	public ResponseEntity<Taldeak> deleteTaldea(@PathVariable String kodea) {
		Optional<Taldeak> taldeOpt = taldeService.find(kodea);

		if (taldeOpt.isPresent()) {
			Taldeak existingTaldea = taldeOpt.get();

			// Obtener los ordutegis asociados al talde
			List<Ordutegiak> ordutegiList = ordutegiService.findByTaldea(existingTaldea);

			// Asignar la fecha de eliminación a los ordutegis
			LocalDateTime now = LocalDateTime.now();
			for (Ordutegiak ordutegi : ordutegiList) {
				ordutegi.setEzabatzeData(now);
				ordutegiService.save(ordutegi);
			}

			// Asignar la fecha de eliminación al talde
			existingTaldea.setEzabatzeData(now);
			taldeService.save(existingTaldea);

			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
