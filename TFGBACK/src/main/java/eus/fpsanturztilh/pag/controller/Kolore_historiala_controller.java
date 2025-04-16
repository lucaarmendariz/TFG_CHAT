package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/kolore_historiala")
@Tag(name = "Kolore historiala", description = "Kolore historiala kudeatzeko kontroladorea")
public class Kolore_historiala_controller {

	@Autowired
	Kolore_historiala_ServiceImpl kolore_historialaService;

	@GetMapping("")
	@Operation(summary = "Kolore historial guztia lortzea", description = "Kolore historial guztiak itzultzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<List<Kolore_historiala>> getAllKoloreHistoriala() {

		List<Kolore_historiala> kolore_historiala_list = kolore_historialaService.getAll();
		return ResponseEntity.ok(kolore_historiala_list);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Kolore historial bat lortzea IDaren arabera", description = "Kolore historial bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Kolore historiala aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Kolore historiala ez da aurkitu") })
	public ResponseEntity<Kolore_historiala> findKoloreHistoriala(@PathVariable Long id) {
		Optional<Kolore_historiala> kolore_historiala_list = kolore_historialaService.find(id);
		if (kolore_historiala_list.isPresent()) {
			return ResponseEntity.ok(kolore_historiala_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Kolore historial berri bat sortzea", description = "Kolore historial berri bat sortzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "201", description = "Kolore historial berria arrakastaz sortu da", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<Kolore_historiala> createKoloreHistoriala(@RequestBody Kolore_historiala kolore_historiala) {
		return ResponseEntity.status(HttpStatus.CREATED).body(kolore_historialaService.save(kolore_historiala));
	}
}
