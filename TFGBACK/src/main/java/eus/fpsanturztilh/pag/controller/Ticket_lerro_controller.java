package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/ticket_lerroak")
@Tag(name = "Ticket Lerroak", description = "Ticket lerroak kudeatzeko kontroladorea")
public class Ticket_lerro_controller {

	@Autowired
	Ticket_lerro_ServiceImpl ticket_lerroaService;

	@Autowired
	Hitzordu_ServiceImpl hitzorduaService;

	@GetMapping("")
	@Operation(summary = "Lortu ticket lerro guztiak", description = "Ticket lerro guztien zerrenda itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Ticket lerroak eskuratu dira") })
	public ResponseEntity<List<Ticket_lerroa>> getAllTicketLerroa() {

		List<Ticket_lerroa> ticket_lerroaList = ticket_lerroaService.getAll();
		return ResponseEntity.ok(ticket_lerroaList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Bilatu ticket lerroa IDaren arabera", description = "ID erabiliz ticket lerroa bilatzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Ticket lerroa aurkitu da"),
			@ApiResponse(responseCode = "404", description = "Ticket lerroa ez da aurkitu") })
	public ResponseEntity<Ticket_lerroa> findTicketLerroa(@PathVariable Long id) {
		Optional<Ticket_lerroa> ticket_lerroaList = ticket_lerroaService.find(id);
		if (ticket_lerroaList.isPresent()) {
			return ResponseEntity.ok(ticket_lerroaList.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Sortu ticket lerroa", description = "Ticket lerro berria sortzen du eta hitzorduaren prezio osoa eguneratzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Ticket lerroa sortu da eta hitzordua eguneratu da"),
			@ApiResponse(responseCode = "404", description = "Hitzordua ez da aurkitu"),
			@ApiResponse(responseCode = "500", description = "Errore interna bat gertatu da") })
	public ResponseEntity<Hitzorduak> createTicketLerroa(@RequestBody List<Ticket_lerroa> ticket_lerroaList) {
		BigDecimal totalPrecio = BigDecimal.ZERO;
		for (Ticket_lerroa lerroa : ticket_lerroaList) {
			Optional<Hitzorduak> citaOptional = hitzorduaService.find(lerroa.getHitzordua().getId());
			if (citaOptional.isPresent()) {
				Hitzorduak cita = citaOptional.get();
				Ticket_lerroa nuevaLinea = ticket_lerroaService.save(lerroa);

				totalPrecio = totalPrecio.add(BigDecimal.valueOf(nuevaLinea.getPrezioa()));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}

		if (!ticket_lerroaList.isEmpty()) {
			Optional<Hitzorduak> citaOptional = hitzorduaService.find(ticket_lerroaList.get(0).getHitzordua().getId());
			if (citaOptional.isPresent()) {
				Hitzorduak cita = citaOptional.get();
				cita.setPrezioTotala(totalPrecio);
				cita.setAmaieraOrduaErreala(LocalTime.now());
				hitzorduaService.save(cita);
				return ResponseEntity.status(HttpStatus.CREATED).body(cita);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

}
