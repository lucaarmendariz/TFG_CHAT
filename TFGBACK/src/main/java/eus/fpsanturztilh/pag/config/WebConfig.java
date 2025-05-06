package eus.fpsanturztilh.pag.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/api/uploads/kategoriak/**")
                .addResourceLocations("file:C:\\Users\\Admin\\Desktop\\TFG_BACK\\TFGBACK\\uploads\\kategoriak");
    }
}
