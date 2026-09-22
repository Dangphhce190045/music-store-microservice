package vn.edu.fpt.mss.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Service API")
                        .description("Microservice managing user music libraries, entitlements and custom playlists")
                        .version("v1.0"));
    }
}
