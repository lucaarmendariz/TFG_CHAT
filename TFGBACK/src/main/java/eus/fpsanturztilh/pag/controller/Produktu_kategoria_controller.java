package eus.fpsanturztilh.pag.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import eus.fpsanturztilh.pag.model.Produktu_Kategoria;
import eus.fpsanturztilh.pag.service.ProduktuKategoriaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/produktu_kategoria")
@Tag(name = "Produktu Kategoria", description = "Produktuaren kategoriak kudeatzeko kontroladorea")
public class Produktu_kategoria_controller {

	@Autowired
	ProduktuKategoriaServiceImpl produktuKatService;

	@Operation(summary = "Produktu kategoria guztiak lortu", description = "Kategori guztien zerrenda eskuratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Kategoria guztiak aurkitu dira", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "Ez dago kategoriarik") })
	@GetMapping("")
	public ResponseEntity<List<Produktu_Kategoria>> getAllProduktuak() {

		List<Produktu_Kategoria> produktuakKatList = produktuKatService.getAll()
			.stream()
	        .filter(kategoria -> kategoria.getEzabatzeData() == null)
	        .map(kategoria -> {
	        	kategoria.setProduktuak(kategoria.getProduktuak()
	                    .stream()
	                    .filter(prod -> prod.getEzabatzeData() == null)
	                    .collect(Collectors.toList()));
	            return kategoria;
	        })
	        .collect(Collectors.toList());
		return ResponseEntity.ok(produktuakKatList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Produktu kategoria bat bilatu IDaren arabera", description = "Kategoria bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Kategoria aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Kategoria ez da aurkitu") })
	public ResponseEntity<Produktu_Kategoria> findProduktu(@PathVariable Long id) {
		Optional<Produktu_Kategoria> produktuakKatList = produktuKatService.find(id);
		if (produktuakKatList.isPresent()) {
			return ResponseEntity.ok(produktuakKatList.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/id/{id}")
	@Operation(summary = "Produktu kategoria bat eguneratu", description = "Dagoen produktu kategoria bat eguneratzen du informazio berriarekin.", responses = {
			@ApiResponse(responseCode = "200", description = "Kategoria eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Kategoria ez da aurkitu") })
	public ResponseEntity<Produktu_Kategoria> editarProduktuKategoria(@RequestBody Produktu_Kategoria kategoria,
			@PathVariable Long id) {
		Optional<Produktu_Kategoria> kategoriaExistente = produktuKatService.find(id);
		if (kategoriaExistente.isPresent()) {
			Produktu_Kategoria categoriaActualizado = kategoriaExistente.get();
			categoriaActualizado.setIzena(kategoria.getIzena());
			produktuKatService.save(categoriaActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(categoriaActualizado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("")
	@Operation(summary = "Produktu kategoria berria sortu", description = "Kategoria berri bat sortzen du eta gorde egiten du datu-basean.", responses = {
			@ApiResponse(responseCode = "201", description = "Kategoria sortu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Datu baliogabeak") })
	public ResponseEntity<Produktu_Kategoria> createProduktua(@RequestBody Produktu_Kategoria produktu) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produktuKatService.save(produktu));
	}

	@DeleteMapping("")
	@Operation(summary = "Produktu kategoria bat ezabatu", description = "Kategoria bat ezabatzen du, ezabatze data ezarriz.", responses = {
			@ApiResponse(responseCode = "200", description = "Kategoria ezabatu da"),
			@ApiResponse(responseCode = "404", description = "Kategoria ez da aurkitu") })
	public ResponseEntity<Void> deleteProduktua(@RequestBody Produktu_Kategoria kat) {
		Optional<Produktu_Kategoria> Kategoria = produktuKatService.find(kat.getId());
		if (Kategoria.isPresent()) {
			Produktu_Kategoria existingKategori = Kategoria.get();
			existingKategori.setEzabatzeData(LocalDateTime.now());
			produktuKatService.save(existingKategori);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
