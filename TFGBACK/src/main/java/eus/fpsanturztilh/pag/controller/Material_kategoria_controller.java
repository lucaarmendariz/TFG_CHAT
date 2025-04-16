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

import eus.fpsanturztilh.pag.model.Material_kategoria;
import eus.fpsanturztilh.pag.service.MaterialKategoriaServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/material_kategoria")
@Tag(name = "Material Kategoria", description = "Materialaren kategoriak kudeatzeko kontroladorea")
public class Material_kategoria_controller {

	@Autowired
	MaterialKategoriaServiceImpl materialKatService;

	@GetMapping("")
    @Operation(summary = "Material Kategoria guztiak lortzea", description = "Material Kategoria guztiak itzultzen ditu, salvo los que tienen ezabatzeData.", responses = {
            @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
    public ResponseEntity<List<Material_kategoria>> getAllMaterialak() {
        List<Material_kategoria> materialKatList = materialKatService.getAll()
        		.stream()
                .filter(kategoria -> kategoria.getEzabatzeData() == null)
                .map(kategoria -> {
                	kategoria.setMaterialak(kategoria.getMaterialak()
                            .stream()
                            .filter(material -> material.getEzabatzeData() == null)
                            .collect(Collectors.toList()));
                    return kategoria;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(materialKatList);
    }

	@GetMapping("/id/{id}")
	@Operation(summary = "Material Kategoria bat lortzea IDaren arabera", description = "Material Kategoria bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Material Kategoria aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Material Kategoria ez da aurkitu") })
	public ResponseEntity<Material_kategoria> findMaterial(@PathVariable Long id) {
		Optional<Material_kategoria> materialKatList = materialKatService.find(id);
		if (materialKatList.isPresent()) {
			return ResponseEntity.ok(materialKatList.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/id/{id}")
	@Operation(summary = "Material Kategoria bat eguneratzea", description = "Material Kategoria bat eguneratzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "200", description = "Material Kategoria arrakastaz eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Material Kategoria ez da aurkitu") })
	public ResponseEntity<Material_kategoria> editarMaterialKategoria(@RequestBody Material_kategoria kategoria,
			@PathVariable Long id) {
		Optional<Material_kategoria> kategoriaExistente = materialKatService.find(id);
		if (kategoriaExistente.isPresent()) {
			Material_kategoria categoriaActualizado = kategoriaExistente.get();
			categoriaActualizado.setIzena(kategoria.getIzena());
			materialKatService.save(categoriaActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(categoriaActualizado);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("")
	@Operation(summary = "Material Kategoria berri bat sortzea", description = "Material Kategoria berri bat sortzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "201", description = "Material Kategoria berria arrakastaz sortu da", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<Material_kategoria> createMaterialKategoria(@RequestBody Material_kategoria material) {
		return ResponseEntity.status(HttpStatus.CREATED).body(materialKatService.save(material));
	}

	@DeleteMapping("/id/{id}")
	@Operation(summary = "Material Kategoria bat ezabatzea", description = "Material Kategoria bat ezabatzen du eta ezabatze data eguneratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Material Kategoria arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Material Kategoria ez da aurkitu") })
	public ResponseEntity<Void> deleteMateriala(@PathVariable Long id) {
		Optional<Material_kategoria> Kategoria = materialKatService.find(id);
		if (Kategoria.isPresent()) {
			Material_kategoria existingKategori = Kategoria.get();
			existingKategori.setEzabatzeData(LocalDateTime.now());
			materialKatService.save(existingKategori);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
