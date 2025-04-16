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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import eus.fpsanturztilh.pag.model.Material_kategoria;
import eus.fpsanturztilh.pag.model.Materialak;
import eus.fpsanturztilh.pag.service.MaterialKategoriaServiceImpl;
import eus.fpsanturztilh.pag.service.MaterialaServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/materialak")
@Tag(name = "Materialak", description = "Materialak kudeatzeko kontroladorea")
public class Materiala_controller {

	@Autowired
	MaterialaServiceImpl materialaService;

	@Autowired
	MaterialKategoriaServiceImpl materialKategoriaService;

	@GetMapping("")
	@Operation(summary = "Material guztiak lortzea", description = "Material guztiak itzultzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })

	public ResponseEntity<List<Materialak>> getAllMaterialak() {

		List<Materialak> materialakList = materialaService.getAll();
		return ResponseEntity.ok(materialakList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Material bat lortzea IDaren arabera", description = "Material bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Materiala aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Materiala ez da aurkitu") })
	public ResponseEntity<Materialak> findMateriala(@PathVariable Long id) {
		Optional<Materialak> materiala_list = materialaService.find(id);
		if (materiala_list.isPresent()) {
			return ResponseEntity.ok(materiala_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Material berri bat sortzea", description = "Material berri bat sortzen du eta kategoria bat gehitzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Materiala sortu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Materialaren kategoria ez da aurkitu") })
	public ResponseEntity<Materialak> createMateriala(@RequestBody Materialak materiala) {
		Long id = materiala.getMaterialKategoria().getId();
		Optional<Material_kategoria> kategoria_list = materialKategoriaService.find(id);
		if (kategoria_list.isPresent()) {
			materiala.setMaterialKategoria(kategoria_list.get());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(materialaService.create(materiala));
	}

	@PutMapping("/id/{id}")
	@Operation(summary = "Material bat eguneratzea", description = "Material bat eguneratzen du eta kategoria berria jartzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Materiala eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Materialaren kategoria ez da aurkitu"),
			@ApiResponse(responseCode = "404", description = "Materiala ez da aurkitu") })
	public ResponseEntity<Materialak> editarMaterial(@PathVariable Long id, @RequestBody Materialak materiala) {
		Optional<Materialak> materialExistente = materialaService.find(id);
		if (materialExistente.isPresent()) {
			Materialak materialActualizado = materialExistente.get();
			materialActualizado.setEtiketa(materiala.getEtiketa());
			materialActualizado.setIzena(materiala.getIzena());
			Long kategoriaId = materiala.getMaterialKategoria().getId();
			Optional<Material_kategoria> categoria = materialKategoriaService.find(kategoriaId);
			if (categoria.isPresent()) {
				materialActualizado.setMaterialKategoria(categoria.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			materialaService.save(materialActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(materialActualizado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/id/{id}")
	@Operation(summary = "Material bat ezabatzea", description = "Material bat ezabatzen du eta ezabatze data gehitzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Materiala arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Materiala ez da aurkitu") })
	public ResponseEntity<Void> deleteMateriala(@PathVariable Long id) {
		Optional<Materialak> material = materialaService.find(id);
		if (material.isPresent()) {
			Materialak existingMaterial = material.get();
			existingMaterial.setEzabatzeData(LocalDateTime.now());
			materialaService.save(existingMaterial);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
