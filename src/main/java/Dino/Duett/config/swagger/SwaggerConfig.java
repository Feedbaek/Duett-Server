package Dino.Duett.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =@Info(
                title = "Duett API 명세서",
                version = "1.0.0",
                description = "Duett API 명세서입니다."
        )
        ,security = { @SecurityRequirement(name = "Authorization") }
)

@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "Authorization",
        in = SecuritySchemeIn.HEADER
)

@Configuration
public class SwaggerConfig {
}
