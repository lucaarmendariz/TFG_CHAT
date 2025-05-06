package eus.fpsanturztilh.pag.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;

@RestController
public class FileServerController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Endpoint para servir la imagen desde el servidor
    @GetMapping("/uploads/kategoriak/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        // Obtén la ruta completa del archivo
        Resource file = new FileSystemResource(Paths.get(uploadDir, filename).toFile());
        
        // Devuelve el archivo como respuesta
        return ResponseEntity.ok(file);
    }
}