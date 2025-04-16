package eus.fpsanturztilh.pag.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.fpsanturztilh.pag.model.Zerbitzu_kategoria;
import eus.fpsanturztilh.pag.service.ZerbitzuKategoriaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/zerbitzu_kategoria")
@CrossOrigin(origins = "http://localhost:8100")
@Tag(name = "Zerbitzuak", description = "Zerbitzuak kudeatzeko kontroladorea")
public class Zerbitzu_kategoria_controller {

	@Autowired
	ZerbitzuKategoriaServiceImpl zerbitzuKategoriaService;

	@GetMapping("")
	@Operation(summary = "Lortu zerbitzu kategoria guztiak", description = "Zerbitzu kategoria guztien zerrenda itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria guztiak eskuratu dira"),
			@ApiResponse(responseCode = "204", description = "Ez dago zerbitzu kategoria eskuragarri") })
	public ResponseEntity<List<Zerbitzu_kategoria>> getAllZerbitzuak() {

		List<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuKategoriaService.getAll();
		return ResponseEntity.ok(zerbitzuKategoriaList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Bilatu zerbitzu kategoria IDaren arabera", description = "ID erabiliz zerbitzu kategoria bat bilatzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria aurkitu da"),
			@ApiResponse(responseCode = "404", description = "Zerbitzu kategoria ez da aurkitu") })
	public ResponseEntity<Zerbitzu_kategoria> findZerbitzuak(@PathVariable Long id) {
		Optional<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuKategoriaService.find(id);
		if (zerbitzuKategoriaList.isPresent()) {
			return ResponseEntity.ok(zerbitzuKategoriaList.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Sortu zerbitzu kategoria berri bat", description = "Zerbitzu kategoria berri bat sortzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Zerbitzu kategoria sortu da") })
	public ResponseEntity<Zerbitzu_kategoria> createZerbitzua(@RequestBody Zerbitzu_kategoria kategoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(zerbitzuKategoriaService.save(kategoria));
	}

	@PutMapping("")
	@Operation(summary = "Eguneratu zerbitzu kategoria", description = "Dagoen zerbitzu kategoria bat eguneratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria eguneratu da"),
			@ApiResponse(responseCode = "404", description = "Zerbitzu kategoria ez da aurkitu") })
	public ResponseEntity<Zerbitzu_kategoria> editarZerbitzua(@RequestBody Zerbitzu_kategoria kategoria) {
		Optional<Zerbitzu_kategoria> kategoriaExistente = zerbitzuKategoriaService.find(kategoria.getId());
		if (kategoriaExistente.isPresent()) {
			Zerbitzu_kategoria kategoriaActualizado = kategoriaExistente.get();
			kategoriaActualizado.setIzena(kategoria.getIzena());
			kategoriaActualizado.setKolorea(kategoria.isKolorea());
			kategoriaActualizado.setExtra(kategoria.isExtra());
			zerbitzuKategoriaService.save(kategoriaActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(kategoriaActualizado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Ezabatu zerbitzu kategoria", description = "Zerbitzu kategoria bat ezabatzen du (logika biguna: ezabatzeData ezartzen da).", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria ezabatu da"),
			@ApiResponse(responseCode = "404", description = "Zerbitzu kategoria ez da aurkitu") })
	public ResponseEntity<Void> deleteZerbitzuKategoria(@PathVariable Long id) {
		Optional<Zerbitzu_kategoria> kategoria = zerbitzuKategoriaService.find(id);
		if (kategoria.isPresent()) {
			Zerbitzu_kategoria existingKategoria = kategoria.get();
			existingKategoria.setEzabatzeData(LocalDateTime.now());
			zerbitzuKategoriaService.save(existingKategoria);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
