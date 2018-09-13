package pl.training.cloud.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.cloud.store.model.Product;
import pl.training.cloud.store.repository.ProductRepository;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableFeignClients(basePackages = "pl.training.cloud")
@EnableBinding(Sink.class)
@EnableSwagger2
@Configuration
public class Beans {

    private static final String BASE_PACKAGE = "pl.training.cloud";

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public Docket productApi() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .build();
    }

    @PostConstruct
    public void init() {
        Product product = new Product();
        product.setName("iPhone Xs");
        product.setPrice(1L);
        product.setQuantity(1000);
        productRepository.save(product);
        product = new Product();
        product.setName("iPhone Xs MAX");
        product.setPrice(88888888L);
        product.setQuantity(1000);
        productRepository.save(product);
    }

}
