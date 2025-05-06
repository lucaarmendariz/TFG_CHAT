package eus.fpsanturztilh.pag.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Subir imagen y actualizar la entidad con la ruta del archivo
    public String uploadImage(Long kategoriaId, MultipartFile file) throws IOException {
    	System.out.println("🛠️ Ruta donde se guardará la imagen: " + uploadDir);

        // Crear el directorio si no existe
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Crear un nombre único para el archivo
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Mostrar el nombre del archivo por consola
        System.out.println("📂 Guardando archivo: " + fileName);

        // Guardar el archivo
        File destination = new File(uploadDir + "/" + fileName);
        file.transferTo(destination);

        return fileName;
    }

}
