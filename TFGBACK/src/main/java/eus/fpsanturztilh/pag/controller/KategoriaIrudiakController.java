package eus.fpsanturztilh.pag.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
public class KategoriaIrudiakController {

    private static final Path UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "uploads", "kategoriak");

    @GetMapping("/api/uploads/kategoriak/list")
    public List<String> listarImagenes() throws IOException {
        if (!Files.exists(UPLOAD_DIR)) {
            return List.of();
        }

        try (var stream = Files.list(UPLOAD_DIR)) {
            return stream
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
        }
    }
}
