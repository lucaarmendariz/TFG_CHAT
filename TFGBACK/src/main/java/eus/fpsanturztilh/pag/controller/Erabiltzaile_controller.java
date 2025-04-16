package eus.fpsanturztilh.pag.controller;

import eus.fpsanturztilh.pag.model.Erabiltzaile;
import eus.fpsanturztilh.pag.service.Erabiltzaile_ServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/erabiltzaileak")
@Tag(name = "Erabiltzailea", description = "Erabiltzaileak kudeatzeko kontroladorea")
public class Erabiltzaile_controller {
	@Autowired
	private Erabiltzaile_ServiceImpl erabiltzaileService;

	@PostMapping("/login")
	@Operation(summary = "Saioa hasteko erabiltzailea egiaztatzea", description = "Erabiltzailearen izena eta pasahitza egiaztatzea, eta arrakasta izanez gero, erabiltzailearen rolarekin batera erantzutea.", responses = {
			@ApiResponse(responseCode = "200", description = "Saioa hasita", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Egiaztapenak faltsuak dira", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<?> login(@RequestBody Erabiltzaile credentials) {
		String username = credentials.getUsername();
		String password = credentials.getPasahitza();
		if (erabiltzaileService.authenticate(username, password)) {
			Optional<Erabiltzaile> erabiltzaile = erabiltzaileService.findByUsername(username);
			if (erabiltzaile.isPresent()) {
				System.out.println("good");
				return ResponseEntity
						.ok(Map.of("username", username, "rola", erabiltzaile.get().getRola(), "status", true));
			}
		}
		return ResponseEntity.status(401).body(Map.of("status", false));
	}

	@PostMapping("/register")
	@Operation(summary = "Erabiltzaile berri bat erregistratzea", description = "Erabiltzaile berri bat erregistratzen du, emandako datuekin, eta erabiltzailea existitzen ez bada, arrakastaz gordetzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Erabiltzailea arrakastaz erregistratua", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Erabiltzailearen datuak falta dira", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "409", description = "Erabiltzailea dagoeneko existitzen da", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Errore bat egon da erabiltzailea erregistratzean", content = @Content(mediaType = "application/json")) })
	public ResponseEntity<?> register(@RequestBody Erabiltzaile credentials) {
		String username = credentials.getUsername();
		String password = credentials.getPasahitza();
		String role = credentials.getRola();

		if (username == null || password == null || role == null) {
			return ResponseEntity.status(400).body("Faltan datos para registrar el usuario");
		}
		Optional<Erabiltzaile> existingUser = erabiltzaileService.findByUsername(username);
		if (existingUser.isPresent()) {
			return ResponseEntity.status(409).body("El usuario ya existe");
		}
		try {
			erabiltzaileService.saveUser(credentials);
			return ResponseEntity.status(201).body("Usuario registrado con éxito");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error al registrar el usuario: " + e.getMessage());
		}
	}
}
