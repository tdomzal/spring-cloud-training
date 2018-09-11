package pl.training.cloud.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@Configuration
public class Beans {

    private static final String BASE_PACKAGE = "pl.training.cloud";

    @Bean
    public Docket productApi() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .build();
    }

}
