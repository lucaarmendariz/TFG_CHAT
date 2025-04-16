package eus.fpsanturztilh.pag.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/produktu_mugimenduak")
@Tag(name = "Produktu Mugimenduak", description = "Produktuaren mugimenduak kudeatzeko kontroladorea")
public class Produktu_mugimendu_controller {

	@Autowired
	Produktu_mugimenduServiceImpl mugimenduService;

	@Autowired
	Produktu_service produktuService;

	@Autowired
	Langile_service langileService;

	@GetMapping("/{fechaDeInicio}/{fechaFin}")
	@Operation(summary = "Lortu produktu mugimendu guztiak", description = "Produktu mugimendu guztien zerrenda itzultzen du.")
	public ResponseEntity<List<Produktu_mugimenduak>> getAllMugimenduak(@PathVariable("fechaDeInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeInicio, 
	        @PathVariable("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

	    // Convertir LocalDate a LocalDateTime (hora 00:00:00 para inicio, 23:59:59 para fin)
	    LocalDateTime fechaInicioDateTime = fechaDeInicio.atStartOfDay();  // 00:00:00
	    LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);     // 23:59:59
	    
		List<Produktu_mugimenduak> mugimenduakList = mugimenduService.getAll();

	    // Filtrar la lista con las fechas y EzbatzeData siendo null
	    List<Produktu_mugimenduak> filteredList = mugimenduakList.stream()
	            .filter(produktua -> {
	                LocalDateTime fecha = produktua.getData();  // Suponiendo que hasieraData es LocalDateTime
	                LocalDateTime ezbatzeData = produktua.getEzabatzeData();  // Supongo que ezbatzeData es LocalDateTime o null

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


		return ResponseEntity.ok(filteredList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Lortu produktu mugimendua ID bidez", description = "Eskaeraren IDarekin bat datorren produktu mugimendua itzultzen du.")
	public ResponseEntity<Produktu_mugimenduak> findMugimenduak(@PathVariable Long id) {
		Optional<Produktu_mugimenduak> mugimendua_list = mugimenduService.find(id);
		if (mugimendua_list.isPresent()) {
			return ResponseEntity.ok(mugimendua_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Erregistratu produktu mugimenduak", description = "Jasotako produktu mugimenduen zerrenda sisteman gordetzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Movimientos registrados correctamente"),
			@ApiResponse(responseCode = "400", description = "Balio ez duten datuak", content = @Content),
			@ApiResponse(responseCode = "500", description = "Zerbitzariaren errorea", content = @Content) })
	public ResponseEntity<String> registrarMovimientos(@RequestBody List<Produktu_mugimenduak> movimientos) {
		try {
			mugimenduService.registrarMovimientos(movimientos);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
