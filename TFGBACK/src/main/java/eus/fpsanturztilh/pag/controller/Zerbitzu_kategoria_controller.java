package eus.fpsanturztilh.pag.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eus.fpsanturztilh.pag.model.Zerbitzu_kategoria;
import eus.fpsanturztilh.pag.service.FileUploadService;
import eus.fpsanturztilh.pag.service.ZerbitzuKategoriaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/zerbitzu_kategoria")
@CrossOrigin(origins = "http://localhost:8100")
@Tag(name = "Zerbitzuak", description = "Zerbitzuak kudeatzeko kontroladorea")
public class Zerbitzu_kategoria_controller {

	@Autowired
	ZerbitzuKategoriaServiceImpl zerbitzuKategoriaService;
	
	@Autowired
    private FileUploadService fileUploadService;
	
	@GetMapping("")
	@Operation(summary = "Lortu zerbitzu kategoria guztiak", description = "Zerbitzu kategoria guztien zerrenda itzultzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria guztiak eskuratu dira"),
			@ApiResponse(responseCode = "204", description = "Ez dago zerbitzu kategoria eskuragarri") })
	public ResponseEntity<List<Zerbitzu_kategoria>> getAllZerbitzuak() {

		List<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuKategoriaService.getAll();
		return ResponseEntity.ok(zerbitzuKategoriaList);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Bilatu zerbitzu kategoria IDaren arabera", description = "ID erabiliz zerbitzu kategoria bat bilatzen du.", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria aurkitu da"),
			@ApiResponse(responseCode = "404", description = "Zerbitzu kategoria ez da aurkitu") })
	public ResponseEntity<Zerbitzu_kategoria> findZerbitzuak(@PathVariable Long id) {
		Optional<Zerbitzu_kategoria> zerbitzuKategoriaList = zerbitzuKategoriaService.find(id);
		if (zerbitzuKategoriaList.isPresent()) {
			return ResponseEntity.ok(zerbitzuKategoriaList.get());
		}
		return ResponseEntity.notFound().build();
	}

	// Endpoint para subir imagen a la categoría
    @PostMapping("/{id}/upload-irudia")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("imagen") MultipartFile file) {

        try {
            // Subir la imagen y obtener el nombre del archivo
            String fileName = fileUploadService.uploadImage(id, file);
            
            // Buscar la categoría por ID y actualizar la imagen
            Optional<Zerbitzu_kategoria> kategoriaOpt = zerbitzuKategoriaService.find(id);
            if (kategoriaOpt.isPresent()) {
                Zerbitzu_kategoria kategoria = kategoriaOpt.get();
                kategoria.setIrudia(fileName);  // Establecer la ruta de la imagen en la entidad
                
                // Guardar la categoría actualizada
                zerbitzuKategoriaService.save(kategoria);
                return ResponseEntity.ok("Imagen subida correctamente: " + fileName);
            } else {
                return ResponseEntity.status(404).body("Categoría no encontrada");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
	
	@PostMapping("")
	@Operation(summary = "Sortu zerbitzu kategoria berri bat", description = "Zerbitzu kategoria berri bat sortzen du.", responses = {
			@ApiResponse(responseCode = "201", description = "Zerbitzu kategoria sortu da") })
	public ResponseEntity<Zerbitzu_kategoria> createZerbitzua(@RequestBody Zerbitzu_kategoria kategoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(zerbitzuKategoriaService.save(kategoria));
	}
	
	@PostMapping("/{id}/assign-image-url")
	public ResponseEntity<Map<String, String>> assignImageUrl(
	        @PathVariable Long id,
	        @RequestBody String imageUrl) {

	    try {
	        // Extraer el nombre del archivo desde la URL
	        String fileName = extractFileName(imageUrl);

	        // Buscar la categoría por ID
	        Optional<Zerbitzu_kategoria> kategoriaOpt = zerbitzuKategoriaService.find(id);
	        if (kategoriaOpt.isPresent()) {
	            Zerbitzu_kategoria kategoria = kategoriaOpt.get();
	            kategoria.setIrudia(fileName);  // Establecer solo el nombre del archivo en la entidad

	            // Guardar la categoría actualizada
	            zerbitzuKategoriaService.save(kategoria);

	            // Responder con un mensaje JSON
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Imagen asignada correctamente");
	            return ResponseEntity.ok(response);  // Respuesta exitosa con mensaje
	        } else {
	            Map<String, String> response = new HashMap<>();
	            response.put("error", "Categoría no encontrada");
	            return ResponseEntity.status(404).body(response);
	        }
	    } catch (Exception e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("error", "Error al asignar la URL de la imagen: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

	// Función para extraer el nombre del archivo desde la URL
	private String extractFileName(String imageUrl) {
	    // Obtener solo el nombre del archivo de la URL (por ejemplo, 'hair-cutting-in-hairdresser-salon-7RCAEVV-scaled.jpg')
	    return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	}




	@PutMapping("/edit-with-image")
	public ResponseEntity<Zerbitzu_kategoria> editarZerbitzuaConImagen(
	    @ModelAttribute Zerbitzu_kategoria kategoria,
	    @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

	    Optional<Zerbitzu_kategoria> kategoriaExistente = zerbitzuKategoriaService.find(kategoria.getId());

	    if (kategoriaExistente.isPresent()) {
	        Zerbitzu_kategoria kategoriaActualizado = kategoriaExistente.get();
	        kategoriaActualizado.setIzena(kategoria.getIzena());
	        kategoriaActualizado.setKolorea(kategoria.isKolorea());
	        kategoriaActualizado.setExtra(kategoria.isExtra());
	        kategoriaActualizado.setEguneratzeData(LocalDateTime.now());

	        if (imagen != null && !imagen.isEmpty()) {
	            try {
	                String fileName = fileUploadService.uploadImage(kategoria.getId(), imagen);
	                kategoriaActualizado.setIrudia(fileName);
	            } catch (IOException e) {
	                return ResponseEntity.status(500).body(null);
	            }
	        } else if (kategoria.getIrudia() != null) {
	            // Conservar imagen existente
	            kategoriaActualizado.setIrudia(kategoria.getIrudia());
	        }

	        zerbitzuKategoriaService.save(kategoriaActualizado);
	        return ResponseEntity.ok(kategoriaActualizado);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Ezabatu zerbitzu kategoria", description = "Zerbitzu kategoria bat ezabatzen du (logika biguna: ezabatzeData ezartzen da).", responses = {
			@ApiResponse(responseCode = "200", description = "Zerbitzu kategoria ezabatu da"),
			@ApiResponse(responseCode = "404", description = "Zerbitzu kategoria ez da aurkitu") })
	public ResponseEntity<Void> deleteZerbitzuKategoria(@PathVariable Long id) {
		Optional<Zerbitzu_kategoria> kategoria = zerbitzuKategoriaService.find(id);
		if (kategoria.isPresent()) {
			Zerbitzu_kategoria existingKategoria = kategoria.get();
			existingKategoria.setEzabatzeData(LocalDateTime.now());
			zerbitzuKategoriaService.save(existingKategoria);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
