package pl.training.cloud.departments.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import pl.training.cloud.departments.model.User;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.logging.Level;
import java.util.logging.Logger;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableBinding(Sink.class)
@EnableCircuitBreaker
@EnableSwagger2
@Configuration
public class Beans {

    private static final String BASE_PACKAGE = "pl.training.cloud";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setBasename("exceptions");
        return messageSource;
    }

    @Bean
    public Docket productApi() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .build();
    }

    @StreamListener(Sink.INPUT)
    public void onNewUserCreated(User user) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "### New user: " + user);
    }

}
