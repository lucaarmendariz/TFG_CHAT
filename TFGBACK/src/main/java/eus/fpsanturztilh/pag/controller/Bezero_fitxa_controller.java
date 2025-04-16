package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.*;
import eus.fpsanturztilh.pag.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bezero_fitxak")
@CrossOrigin(origins = "http://localhost:8100") // Ionic-etik eskaerak egiteko aukera ematen du
@Tag(name = "Bezero fitxa", description = "Bezero fitxa kudeatzeko kontroladorea")
public class Bezero_fitxa_controller {

	@Autowired
	Bezero_fitxa_ServiceImpl bezero_fitxaService;

	@Autowired
	Kolore_historiala_service kolore_historialaService;

	@Autowired
	ProduktuServiceImpl produktuaService;

	@GetMapping("")
    @Operation(summary = "Bezero fitxa guztiak lortzea", description = "Bezero fitxa guztiak itzultzen ditu, salvo los que tienen ezabatzeData. Además, filtra el historial interno para eliminar elementos con ezabatzeData.", responses = {
            @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
    public ResponseEntity<List<Bezero_fitxa>> getAllBezeroFitxak() {
        List<Bezero_fitxa> bezero_fitxa_list = bezero_fitxaService.getAll()
                .stream()
                .filter(bezero -> bezero.getEzabatzeData() == null)
                .map(bezero -> {
                    bezero.setHistoriala(bezero.getHistoriala()
                            .stream()
                            .filter(historial -> historial.getEzabatzeData() == null)
                            .collect(Collectors.toList()));
                    return bezero;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(bezero_fitxa_list);
    }

	@GetMapping("/id/{id}")
	@Operation(summary = "Bezero fitxa bat lortzea IDaren arabera", description = "Bezero fitxa bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Bezero fitxa aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Bezero fitxa ez da aurkitu") })
	public ResponseEntity<Bezero_fitxa> findBezeroFitxak(@PathVariable Long id) {
		Optional<Bezero_fitxa> bezero_fitxa_list = bezero_fitxaService.find(id);
		if (bezero_fitxa_list.isPresent()) {
			return ResponseEntity.ok(bezero_fitxa_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Bezero fitxa berri bat sortzea", description = "Bezero fitxa berri bat sortzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "201", description = "Bezero fitxa sortu da arrakastaz", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<Bezero_fitxa> createBezeroFitxak(@RequestBody Bezero_fitxa bezero_fitxa) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bezero_fitxaService.save(bezero_fitxa));
	}

	@PutMapping("")
	@Operation(summary = "Bezero fitxa bat eguneratzea", description = "IDarekin adierazitako bezero fitxa eguneratzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Bezero fitxa arrakastaz eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Bezero fitxa ez da aurkitu") })
	public ResponseEntity<Bezero_fitxa> updateBezero(@RequestBody Bezero_fitxa bezeroFitxa) {
		Optional<Bezero_fitxa> existingBezero = bezero_fitxaService.find(bezeroFitxa.getId());
		if (existingBezero.isPresent()) {
			Bezero_fitxa bezero = existingBezero.get();
			bezero.setIzena(bezeroFitxa.getIzena());
			bezero.setAbizena(bezeroFitxa.getAbizena());
			bezero.setTelefonoa(bezeroFitxa.getTelefonoa());
			bezero.setAzalSentikorra(bezeroFitxa.getAzalSentikorra());

			// Historialak eguneratuta edukitzeko zerrenda
			List<Kolore_historiala> updatedHistoriala = new ArrayList<>();

			for (Kolore_historiala hist : bezeroFitxa.getHistoriala()) {
				if (hist.getId() == null) {
					// ID ez badu, historia berria da -> Gehitu
					hist.setBezeroa(bezero);
					updatedHistoriala.add(hist);
				} else {
					// Lehendik badago, bilatu eta eguneratu
					Optional<Kolore_historiala> existingHist = kolore_historialaService.find(hist.getId());
					if (existingHist.isPresent()) {
						Kolore_historiala existing = existingHist.get();
						existing.setData(hist.getData());
						existing.setKantitatea(hist.getKantitatea());
						existing.setBolumena(hist.getBolumena());
						existing.setOharrak(hist.getOharrak());

						// Ezabatu bada, eguneratu ezabatze_data
						if (hist.getEzabatzeData() != null) {
							existing.setEzabatzeData(LocalDateTime.now());
						}
						// Guardar historial actualizado
						kolore_historialaService.save(existing);
						updatedHistoriala.add(existing);
					}
				}
			}

			// Ordeztu bezeroaren historia
			bezero.getHistoriala().clear();
			bezero.getHistoriala().addAll(updatedHistoriala);

			// Gorde bezeroa historia eguneratuarekin
			bezero_fitxaService.save(bezero);
			return ResponseEntity.ok(bezero);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("")
	@Operation(summary = "Bezero fitxa bat ezabatzea", description = "Bezero fitxa bat ezabatuta markatzen du, ezabatzeko data ezarriz.", responses = {
			@ApiResponse(responseCode = "200", description = "Bezero fitxa arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Bezero fitxa ez da aurkitu") })
	public ResponseEntity<Bezero_fitxa> deleteCita(@RequestBody Bezero_fitxa bezero) {
		Optional<Bezero_fitxa> bezeroExistente = bezero_fitxaService.find(bezero.getId());
		if (bezeroExistente.isPresent()) {
			Bezero_fitxa bezeroa = bezeroExistente.get();
			bezeroa.setEzabatzeData(LocalDateTime.now());
			Bezero_fitxa bezeroActualizada = bezero_fitxaService.save(bezeroa);
			return ResponseEntity.ok(bezeroActualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
