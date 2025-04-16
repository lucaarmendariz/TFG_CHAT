package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/ordutegiak")
@Tag(name = "Ordutegiak", description = "Ordutegiak kudeatzeko kontroladorea")
public class Ordutegi_controller {

	@Autowired
	Ordutegi_ServiceImpl ordutegiService;

	@Autowired
	Talde_service taldeService;

	@GetMapping("")
	@Operation(summary = "Ordutegi guztiak lortzea", description = "Ordutegi guztiak itzultzen ditu. Ez badira aurkitzen, 204 No Content itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "204", description = "Ez dago ordutegirik") })

	public ResponseEntity<List<Ordutegiak>> getAllOrdutegiak() {
		List<Ordutegiak> ordutegiList = ordutegiService.getAll();
		if (ordutegiList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(ordutegiList);
	}

	@GetMapping("/{fechaDeInicio}/{fechaFin}")
	@Operation(summary = "Ordutegi guztiak lortzea", description = "Ordutegi guztiak itzultzen ditu. Ez badira aurkitzen, 204 No Content itzultzen du.", responses = {
	        @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")),
	        @ApiResponse(responseCode = "204", description = "Ez dago ordutegirik") })
	public ResponseEntity<List<Ordutegiak>> getAllOrdutegiak(@PathVariable("fechaDeInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeInicio, 
	        @PathVariable("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

	    // Convertir LocalDate a LocalDateTime (hora 00:00:00 para inicio, 23:59:59 para fin)
	    LocalDateTime fechaInicioDateTime = fechaDeInicio.atStartOfDay();  // 00:00:00
	    LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);     // 23:59:59

	    List<Ordutegiak> ordutegiList = ordutegiService.getAll();

	    // Filtrar la lista con las fechas y 'ezabatzeData' siendo null
	    List<Ordutegiak> filteredList = ordutegiList.stream()
	            .filter(ordutegia -> {
	                LocalDateTime hasieraData = ordutegia.getHasieraData().atStartOfDay();  // 'hasieraData' a LocalDateTime (con hora 00:00:00)
	                LocalDateTime amaieraData = ordutegia.getAmaieraData().atTime(23, 59, 59);  // 'amaieraData' a LocalDateTime (con hora 23:59:59)
	                LocalDateTime ezabatzeData = ordutegia.getEzabatzeData();  // 'ezabatzeData' a LocalDateTime o null
	                boolean isTaldeEzabatzeDataNull = ordutegia.getTaldea() != null && ordutegia.getTaldea().getEzabatzeData() == null;

	                // Comprobar si alguna de las fechas (inicio o fin) está dentro del rango
	                boolean isInRange = 
	                        // Caso 1: Si 'hasieraData' está dentro del rango
	                        (fechaInicioDateTime.isBefore(amaieraData) || fechaInicioDateTime.isEqual(amaieraData)) &&
	                        (fechaFinDateTime.isAfter(hasieraData) || fechaFinDateTime.isEqual(hasieraData)) ||
	                        // Caso 2: Si 'amaieraData' está dentro del rango
	                        (fechaInicioDateTime.isBefore(amaieraData) || fechaInicioDateTime.isEqual(amaieraData)) &&
	                        (fechaFinDateTime.isAfter(hasieraData) || fechaFinDateTime.isEqual(hasieraData)) ||
	                        // Caso 3: Si ambos 'hasieraData' y 'amaieraData' están dentro del rango
	                        (fechaInicioDateTime.isBefore(amaieraData) || fechaInicioDateTime.isEqual(amaieraData)) &&
	                        (fechaFinDateTime.isAfter(hasieraData) || fechaFinDateTime.isEqual(hasieraData));

	                // Filtrar solo los elementos cuya fecha esté en el rango y 'ezabatzeData' sea null en taldea
	                return isInRange && ezabatzeData == null && isTaldeEzabatzeDataNull;
	            })
	            .collect(Collectors.toList());

	    return ResponseEntity.ok(filteredList);
	}
	
	@GetMapping("/id/{id}")
	@Operation(summary = "Ordutegi bat lortzea IDaren arabera", description = "Ordutegi bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Ordutegia aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Ordutegia ez da aurkitu") })
	public ResponseEntity<Ordutegiak> getOrdutegiById(@PathVariable Long id) {
		Optional<Ordutegiak> ordutegi = ordutegiService.find(id);
		if (ordutegi.isPresent()) {
			return ResponseEntity.ok(ordutegi.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/id/{id}")
	@Operation(summary = "Ordutegi bat eguneratzea IDaren arabera", description = "Ordutegi bat eguneratzen du. Aurkitzen bada, eguneratu eta itzuli.", responses = {
			@ApiResponse(responseCode = "200", description = "Ordutegia eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Ordutegia ez da aurkitu") })
	public ResponseEntity<Ordutegiak> updateOrdutegi(@PathVariable Long id, @RequestBody Ordutegiak ordutegi) {
		Optional<Ordutegiak> existingOrdutegi = ordutegiService.find(id);

		if (existingOrdutegi.isPresent()) {
			ordutegi.setId(id);
			Ordutegiak updatedOrdutegi = ordutegiService.save(ordutegi);
			return ResponseEntity.ok(updatedOrdutegi);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Ordutegi berri bat sortzea", description = "Ordutegi berri bat sortzen du. Sartutako datuak baliozkotzen ditu.", responses = {
			@ApiResponse(responseCode = "201", description = "Ordutegia sortu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Sartutako datuak ez dira baliodunak"),
			@ApiResponse(responseCode = "500", description = "Barruko zerbitzu errorea") })
	public ResponseEntity<?> createOrdutegiak(@RequestBody Ordutegiak ordutegiak) {
		try {
			if (ordutegiak == null || ordutegiak.hasInvalidFields()) {
				return new ResponseEntity<>("Datos de entrada no válidos", HttpStatus.BAD_REQUEST);
			}

			Ordutegiak createdOrdutegiak = ordutegiService.save(ordutegiak);
			return new ResponseEntity<>(createdOrdutegiak, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("Valores incorrectos en la petición", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/id/{id}")
	@Operation(summary = "Ordutegi bat ezabatzea", description = "Ordutegi bat ezabatzen du IDaren arabera. Horretarako ezabatze data eguneratzen da.", responses = {
			@ApiResponse(responseCode = "200", description = "Ordutegia arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Ordutegia ez da aurkitu") })
	public ResponseEntity<String> deleteOrdutegi(@PathVariable Long id) {
		Optional<Ordutegiak> ordutegi = ordutegiService.find(id);

		if (ordutegi.isPresent()) {
			Ordutegiak horario = ordutegi.get();
			horario.setEzabatzeData(LocalDateTime.now());
			ordutegiService.save(horario);
			return ResponseEntity.ok("Horario eliminado correctamente");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Horario no encontrado");
	}

}
