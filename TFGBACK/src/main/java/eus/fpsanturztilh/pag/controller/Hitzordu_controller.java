package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/hitzorduak")
@Tag(name = "Hitzorduak", description = "Hitzorduak kudeatzeko kontroladorea")
public class Hitzordu_controller {

	@Autowired
	Hitzordu_ServiceImpl hitzorduService;

	@Autowired
	Langile_ServiceImpl langileService;

	@GetMapping("")
	@Operation(summary = "Hitzordu guztiak lortzea", description = "Hitzordu guztiak itzultzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<List<Hitzorduak>> getAllHitzorduak() {

		List<Hitzorduak> hitzorduakList = hitzorduService.getAll();
		return ResponseEntity.ok(hitzorduakList);
	}
	
	@GetMapping("/date/{date}")
    @Operation(summary = "Filtrar hitzorduak por fecha", description = "Devuelve los hitzorduak que coinciden con la fecha proporcionada.", responses = {
            @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    public ResponseEntity<List<Hitzorduak>> getHitzorduakByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Hitzorduak> filteredHitzorduak = hitzorduService.getByDate(date);
        return ResponseEntity.ok(filteredHitzorduak);
    }

	@GetMapping("/id/{id}")
	@Operation(summary = "Hitzordu bat lortzea IDaren arabera", description = "Hitzordu bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Hitzordua aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Hitzordua ez da aurkitu") })
	public ResponseEntity<Hitzorduak> findHitzorduak(@PathVariable Long id) {
		Optional<Hitzorduak> hitzordua_list = hitzorduService.find(id);
		if (hitzordua_list.isPresent()) {
			return ResponseEntity.ok(hitzordua_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/ticket/{fechaDeInicio}/{fechaFin}")
	@Operation(summary = "Prezioa duten hitzorduak lortzea", description = "Prezioa duten hitzordu guztiak itzultzen ditu.", responses = {
	        @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<List<Hitzorduak>> getHitzorduakConPrecio(@PathVariable("fechaDeInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeInicio, 
	        @PathVariable("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
	    
	    // Convertir LocalDate a LocalDateTime (hora 00:00:00 para inicio, 23:59:59 para fin)
	    LocalDateTime fechaInicioDateTime = fechaDeInicio.atStartOfDay();  // 00:00:00
	    LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);     // 23:59:59

	    List<Hitzorduak> hitzorduakList = hitzorduService.getAll();
	    List<Hitzorduak> citasConPrecio = new ArrayList<>();
	    
	    // Filtrar la lista con las fechas y PrecioTotal no nulo
	    for (Hitzorduak hitzordu : hitzorduakList) {
	        if (hitzordu.getPrezioTotala() != null) {
	            LocalDate fecha = hitzordu.getData();  // Suponiendo que 'data' es LocalDate

	            // Log para ver las fechas comparadas
	            System.out.println("Fecha de inicio: " + fechaInicioDateTime + ", Fecha de fin: " + fechaFinDateTime);
	            System.out.println("Fecha de hitzordu: " + fecha);

	            // Filtrar solo los elementos cuya fecha esté en el rango
	            if (!fecha.isBefore(fechaDeInicio) && !fecha.isAfter(fechaFin)) {
	                citasConPrecio.add(hitzordu);
	            }
	        }
	    }

	    return ResponseEntity.ok(citasConPrecio);
	}


	@GetMapping("/langileZerbitzuak")
	@Operation(summary = "Zerbitzuak kategoria bakoitzean lortzea", description = "Zerbitzuak kategoria bakoitzean lortutako mapa itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })

	public Map<Long, Map<String, Object>> getServiciosPorCategoria() {

		return hitzorduService.obtenerServiciosPorCategoriaAgrupado();
	}

	@GetMapping("/hoy")
	@Operation(summary = "Gaurko hitzorduak lortzea", description = "Gaurko datarekin eta ezabatzeko data ez duten hitzorduak itzultzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<List<Hitzorduak>> getHitzorduakDeHoy() {
		LocalDate hoy = LocalDate.now();
		List<Hitzorduak> hitzorduakList = hitzorduService.getAll();

		List<Hitzorduak> citasDeHoy = hitzorduakList.stream()
				.filter(h -> h.getData().equals(hoy) && h.getEzabatzeData() == null).toList();

		return ResponseEntity.ok(citasDeHoy);
	}

	@PostMapping("")
	@Operation(summary = "Hitzordu berri bat sortzea", description = "Hitzordu berri bat sortzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "201", description = "Hitzordua arrakastaz sortu da", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<Hitzorduak> createHitzorduak(@RequestBody Hitzorduak hitzordu) {
		return ResponseEntity.status(HttpStatus.CREATED).body(hitzorduService.save(hitzordu));
	}

	@PutMapping("")
	@Operation(summary = "Hitzordu bat eguneratzea", description = "IDarekin adierazitako hitzordua eguneratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Hitzordua arrakastaz eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Hitzordua ez da aurkitu") })
	public ResponseEntity<Hitzorduak> actualizarCita(@RequestBody Hitzorduak hitzordu) {
		Optional<Hitzorduak> citaExistente = hitzorduService.find(hitzordu.getId());

		if (citaExistente.isPresent()) {
			Hitzorduak cita = citaExistente.get();

			cita.setEserlekua(hitzordu.getEserlekua());
			cita.setData(hitzordu.getData());
			cita.setHasieraOrdua(hitzordu.getHasieraOrdua());
			cita.setAmaieraOrdua(hitzordu.getAmaieraOrdua());
			cita.setIzena(hitzordu.getIzena());
			cita.setTelefonoa(hitzordu.getTelefonoa());
			cita.setDeskribapena(hitzordu.getDeskribapena());
			cita.setEtxekoa(hitzordu.getEtxekoa());

			Hitzorduak citaActualizada = hitzorduService.save(cita);
			return ResponseEntity.ok(citaActualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/asignar/{id_langile}")
	@Operation(summary = "Hitzordu bat langile bati esleitzea", description = "Hitzordu bat langile bati esleitzen dio, langilea aurkitzen bada.", responses = {
			@ApiResponse(responseCode = "200", description = "Hitzordua arrakastaz esleitu da langileari", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Hitzordua edo langilea ez da aurkitu") })
	public ResponseEntity<Hitzorduak> asignarCita(@RequestBody Hitzorduak hitzordu, @PathVariable Long id_langile) {
		Optional<Hitzorduak> citaExistente = hitzorduService.find(hitzordu.getId());
		if (citaExistente.isPresent()) {
			Hitzorduak cita = citaExistente.get();
			Optional<Langileak> langileExistente = langileService.find(id_langile);
			if (langileExistente.isPresent()) {
				Langileak langile = langileExistente.get();
				cita.setLangilea(langile);
				cita.setHasieraOrduaErreala(LocalTime.now());
				Hitzorduak citaActualizada = hitzorduService.save(cita);
				return ResponseEntity.ok(citaActualizada);
			}
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("")
	@Operation(summary = "Hitzordu bat ezabatzea", description = "Hitzordu bat ezabatuta markatzen du, ezabatzeko data ezarriz.", responses = {
			@ApiResponse(responseCode = "200", description = "Hitzordua arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Hitzordua ez da aurkitu") })
	public ResponseEntity<Hitzorduak> deleteCita(@RequestBody Hitzorduak hitzordu) {
		Optional<Hitzorduak> citaExistente = hitzorduService.find(hitzordu.getId());
		if (citaExistente.isPresent()) {
			Hitzorduak cita = citaExistente.get();
			cita.setEzabatzeData(LocalDateTime.now());
			Hitzorduak citaActualizada = hitzorduService.save(cita);
			return ResponseEntity.ok(citaActualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
