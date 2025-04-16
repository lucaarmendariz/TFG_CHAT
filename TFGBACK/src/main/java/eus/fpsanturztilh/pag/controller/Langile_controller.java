package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import eus.fpsanturztilh.pag.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/api/langileak")
@Tag(name = "Langileak", description = "Langileak kudeatzeko kontroladorea")
public class Langile_controller {

	@Autowired
	Langile_ServiceImpl langileService;

	@Autowired
	Talde_service taldeService;

	@Autowired
	Txandak_service txandakService;

	@GetMapping("")
    @Operation(summary = "Langile guztiak lortzea", description = "Langile guztien zerrenda itzultzen du, filtrando los que tienen ezabatzeData.", responses = {
            @ApiResponse(responseCode = "200", description = "Eragiketa arrakastatsua", content = @Content(mediaType = "application/json")) })
    public ResponseEntity<List<Langileak>> getAllLangileak() {
        List<Langileak> langileakList = langileService.getAll()
                .stream()
                .filter(langile -> langile.getEzabatzeData() == null)
                .collect(Collectors.toList());
        return ResponseEntity.ok(langileakList);
    }

	@GetMapping("/id/{id}")
	@Operation(summary = "Langile bat lortzea IDaren arabera", description = "Langile bat bilatzen du IDarekin eta aurkitzen bada itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Langilea aurkitu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Langilea ez da aurkitu") })
	public ResponseEntity<Langileak> findLangilea(@PathVariable Long id) {
		Optional<Langileak> langile_list = langileService.find(id);
		if (langile_list.isPresent()) {
			return ResponseEntity.ok(langile_list.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("")
	@Operation(summary = "Langile berri bat sortzea", description = "Langile berri bat sortzen du emandako datuekin.", responses = {
			@ApiResponse(responseCode = "201", description = "Langile berria arrakastaz sortu da", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<Langileak> createLangilea(@RequestBody Langileak langile) {
		String kodea = langile.getTaldea().getKodea();
		Optional<Taldeak> talde_list = taldeService.find(kodea);
		if (talde_list.isPresent()) {
			langile.setTaldea(talde_list.get());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(langileService.save(langile));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Langile bat eguneratzea", description = "Langile bat eguneratzen du emandako datuekin, Taldea eta bestelako informazioak eguneratuta.", responses = {
			@ApiResponse(responseCode = "200", description = "Langilea arrakastaz eguneratu da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Langilea ez da aurkitu"),
			@ApiResponse(responseCode = "400", description = "Taldea ez da existitzen") })
	public ResponseEntity<Langileak> updateLangilea(@PathVariable Long id, @RequestBody Langileak langileUpdated) {
		// Bilatu Langilea bere IDaren bidez
		Optional<Langileak> existingLangileaOpt = langileService.find(id);

		if (existingLangileaOpt.isPresent()) {
			// Langilea existitzen bada, egungo helburua lortuko dugu
			Langileak existingLangilea = existingLangileaOpt.get();

			// Langilearen eremuak balio berriekin eguneratzen ditugu
			existingLangilea.setIzena(langileUpdated.getIzena());
			existingLangilea.setAbizenak(langileUpdated.getAbizenak());

			// Taldearekiko harremana eguneratzen dugu talde berri bat ematen bada
			if (langileUpdated.getTaldea() != null) {
				String newKodea = langileUpdated.getTaldea().getKodea();
				Optional<Taldeak> newTalde = taldeService.find(newKodea);
				if (newTalde.isPresent()) {
					existingLangilea.setTaldea(newTalde.get());
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Taldearen kodea ez bada
																						// baliozkoa
				}
			}

			// Erlazio-zerrendak eguneratzea (nahiz eta adibide honetan ez ditugun balioak
			// aldatzen)
			// Hemen eguneratu daitezke, beharrezkoa bada:
			// - Erlazionatutako produktuak (Produktu_mugimenduak)
			// - Lotutako transakzioak (Txandak)

			// Produktu_mugimenduak eta Txandak zerrendak eguneratu nahi izanez gero,
			// antzera egin daiteke.
			// Zerrenda horiek zuzenean aldatu behar ez direla suposatuz, ez ditugu
			// esplizituki eguneratzen.

			// Dagozkion datak eguneratzen ditugu

			existingLangilea.setEguneratzeData(LocalDateTime.now()); // Eguneratze-data

			// Langilea eguneratuta gordeko dugu
			Langileak updatedLangilea = langileService.save(existingLangilea);

			// 200 OK egoera-kodearekin eguneratutako langilea itzuliko dugu
			return ResponseEntity.ok(updatedLangilea);
		} else {
			// Langilea ez badago, 404 Not Found batekin erantzungo dugu
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Langile bat ezabatzea", description = "Langile bat ezabatzen du eta, hala badagokio, horri lotutako transakzioak eguneratzen ditu.", responses = {
			@ApiResponse(responseCode = "200", description = "Langilea arrakastaz ezabatuta", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Langilea ez da aurkitu") })
	public ResponseEntity<Langileak> deleteLangilea(@PathVariable Long id) {
		Optional<Langileak> langileOpt = langileService.find(id);

		if (langileOpt.isPresent()) {
			Langileak existingLangilea = langileOpt.get();

			// Langilearekin lotutako txandak lortu
			List<Txandak> txandakList = txandakService.findByLangile(existingLangilea);

			// Txandak ezabatzeko data esleitu
			LocalDateTime now = LocalDateTime.now();
			for (Txandak txanda : txandakList) {
				txanda.setEzabatzeData(now);
				txandakService.update(txanda);
			}

			// Esleitu ezabatze-data langileari
			existingLangilea.setEzabatzeData(now);
			langileService.save(existingLangilea);

			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}