package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/material_mailegua")
@Tag(name = "Material Mailegua", description = "Material maileguak kudeatzeko kontroladorea")
public class Material_mailegua_controller {

	@Autowired
	Material_mailegu_ServiceImpl mailegua_Service;

	@GetMapping("")
	@Operation(summary = "Material Mailegua guztiak lortzea", description = "Material Mailegua guztiak itzultzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<List<Material_mailegua>> getAllMaileguak() {
		List<Material_mailegua> mailegua_list = mailegua_Service.getAll();
		return ResponseEntity.ok(mailegua_list);
	}
	
	@GetMapping("/{fechaDeInicio}/{fechaFin}")
	@Operation(summary = "Material Mailegua guztiak lortzea", 
	           description = "Material Mailegua guztiak itzultzen ditu, hasieraData eta ezbatzeData kontuan hartuta.", 
	           responses = {
	               @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) 
	           })
	public ResponseEntity<List<Material_mailegua>> getAllMaileguak(
	        @PathVariable("fechaDeInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeInicio, 
	        @PathVariable("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

	    // Convertir LocalDate a LocalDateTime (hora 00:00:00 para inicio, 23:59:59 para fin)
	    LocalDateTime fechaInicioDateTime = fechaDeInicio.atStartOfDay();  // 00:00:00
	    LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);     // 23:59:59

	    // Recuperar todos los materiales de la base de datos
	    List<Material_mailegua> mailegua_list = mailegua_Service.getAll();

	    // Filtrar la lista con las fechas y EzbatzeData siendo null
	    List<Material_mailegua> filteredList = mailegua_list.stream()
	            .filter(mailegua -> {
	                LocalDateTime fecha = mailegua.getHasieraData();  // Suponiendo que hasieraData es LocalDateTime
	                LocalDateTime ezbatzeData = mailegua.getEzabatzeData();  // Supongo que ezbatzeData es LocalDateTime o null

	                // Log para ver las fechas comparadas
	                System.out.println("Fecha de inicio: " + fechaInicioDateTime + ", Fecha de fin: " + fechaFinDateTime);
	                System.out.println("Fecha de material: " + fecha);
	                System.out.println("EzbatzeData: " + ezbatzeData);

	                // Filtrar solo los elementos cuyo hasieraData esté en el rango y EzbatzeData sea null
	                return !fecha.toLocalDate().isBefore(fechaDeInicio) 
	                        && !fecha.toLocalDate().isAfter(fechaFin) 
	                        && ezbatzeData == null;
	            })
	            .collect(Collectors.toList());

	    // Retornar la lista filtrada
	    return ResponseEntity.ok(filteredList);
	}



	@GetMapping("/id/{id}")
	@Operation(summary = "Material Mailegua bat lortzea IDaren arabera", description = "Material Mailegua bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Material Mailegua aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Material Mailegua ez da aurkitu") })
	public ResponseEntity<Material_mailegua> findMaileguak(@PathVariable Long id) {
		Optional<Material_mailegua> mailegua_list = mailegua_Service.find(id);
		if (mailegua_list.isPresent()) {
			return ResponseEntity.ok(mailegua_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("")
	@Operation(summary = "Mugimenduak bukatzea", description = "Material Maileguen mugimenduak amaitzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Mugimenduak arrakastaz bukatu dira"),
			@ApiResponse(responseCode = "400", description = "Errore bat gertatu da datuak prozesatzean"),
			@ApiResponse(responseCode = "500", description = "Errore barneko zerbitzu baten arazo bat gertatu da") })
	public ResponseEntity<String> terminarMugimenduak(@RequestBody List<Material_mailegua> mugimenduak) {
		try {
			mailegua_Service.terminarMovimientos(mugimenduak);

			return new ResponseEntity<>("Mugimenduak amaitu dira", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("")
	@Operation(summary = "Mugimenduak erregistratzea", description = "Material Maileguen mugimenduak erregistratzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Mugimenduak arrakastaz erregistratu dira"),
			@ApiResponse(responseCode = "400", description = "Errore bat gertatu da datuak prozesatzean"),
			@ApiResponse(responseCode = "500", description = "Errore barneko zerbitzu baten arazo bat gertatu da") })
	public ResponseEntity<String> registrarMovimientos(@RequestBody List<Material_mailegua> movimientos) {
		try {
			mailegua_Service.registrarMovimientos(movimientos);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
