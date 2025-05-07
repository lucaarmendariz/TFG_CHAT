package eus.fpsanturztilh.pag.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ruta relativa convertida a absoluta URI
        String uploadDir = Paths.get("uploads/kategoriak").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/api/uploads/kategoriak/**")
                .addResourceLocations(uploadDir);
    }
}
