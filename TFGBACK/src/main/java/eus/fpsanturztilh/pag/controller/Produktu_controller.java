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

import eus.fpsanturztilh.pag.model.Produktu_Kategoria;
import eus.fpsanturztilh.pag.model.Produktuak;
import eus.fpsanturztilh.pag.service.ProduktuKategoriaServiceImpl;
import eus.fpsanturztilh.pag.service.ProduktuServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/produktuak")
@Tag(name = "Produktuak", description = "Produktuak kudeatzeko kontroladorea")
public class Produktu_controller {

	@Autowired
	ProduktuServiceImpl produktuService;

	@Autowired
	ProduktuKategoriaServiceImpl produktuKategoriaService;

	@GetMapping("")
	@Operation(summary = "Produktu guztiak lortzea", description = "Produktu guztien zerrenda eskuratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Produktuak aurkitu dira", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "Ez dago produkturik") })
	public ResponseEntity<List<Produktuak>> getAllProduktuak() {

		List<Produktuak> produktuakList = produktuService.getAll();
		return ResponseEntity.ok(produktuakList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Produktu bat lortzea IDaren arabera", description = "Produktu bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Produktua aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Produktua ez da aurkitu") })
	public ResponseEntity<Produktuak> findProduktu(@PathVariable Long id) {
		Optional<Produktuak> produktua_list = produktuService.find(id);
		if (produktua_list.isPresent()) {
			return ResponseEntity.ok(produktua_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Produktu berri bat sortzea", description = "Produktu berri bat sortzen du eta gorde egiten du datu-basean.", responses = {
			@ApiResponse(responseCode = "201", description = "Produktua sortu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Datu baliogabeak") })
	public ResponseEntity<Produktuak> createProduktua(@RequestBody Produktuak produktu) {
		Long id = produktu.getProduktuKategoria().getId();
		Optional<Produktu_Kategoria> kategoria_list = produktuKategoriaService.find(id);
		if (kategoria_list.isPresent()) {
			produktu.setProduktuKategoria(kategoria_list.get());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(produktuService.save(produktu));
	}

	@PutMapping("")
	@Operation(summary = "Produktu bat eguneratzea", description = "Dagoen produktu bat eguneratzen du informazio berriarekin.", responses = {
			@ApiResponse(responseCode = "200", description = "Produktua eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Datu baliogabeak"),
			@ApiResponse(responseCode = "404", description = "Produktua ez da aurkitu") })
	public ResponseEntity<Produktuak> editarProducto(@RequestBody Produktuak producto) {
		Optional<Produktuak> productoExistente = produktuService.find(producto.getId());
		if (productoExistente.isPresent()) {
			Produktuak productoActualizado = productoExistente.get();
			productoActualizado.setIzena(producto.getIzena());
			productoActualizado.setDeskribapena(producto.getDeskribapena());
			productoActualizado.setMarka(producto.getMarka());
			productoActualizado.setStock(producto.getStock());
			productoActualizado.setStockAlerta(producto.getStockAlerta());
			Long kategoriaId = producto.getProduktuKategoria().getId();
			Optional<Produktu_Kategoria> categoria = produktuKategoriaService.find(kategoriaId);
			if (categoria.isPresent()) {
				productoActualizado.setProduktuKategoria(categoria.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			produktuService.save(productoActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(productoActualizado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("")
	@Operation(summary = "Produktu bat ezabatzea", description = "Produktu bat ezabatzen du, ezabatze data eguneratuz.", responses = {
			@ApiResponse(responseCode = "200", description = "Produktua ezabatu da"),
			@ApiResponse(responseCode = "404", description = "Produktua ez da aurkitu") })
	public ResponseEntity<Void> deleteProduktua(@RequestBody Produktuak produkto) {
		Optional<Produktuak> produktua = produktuService.find(produkto.getId());
		if (produktua.isPresent()) {
			Produktuak existingProduct = produktua.get();
			existingProduct.setEzabatzeData(LocalDateTime.now());
			produktuService.save(existingProduct);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
