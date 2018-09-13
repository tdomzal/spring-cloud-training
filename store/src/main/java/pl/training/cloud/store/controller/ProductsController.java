package pl.training.cloud.store.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.training.cloud.store.dto.ProductDto;
import pl.training.cloud.store.model.Mapper;
import pl.training.cloud.store.service.ProductsService;

import java.util.List;

@RequestMapping("store")
@RequiredArgsConstructor
@RestController
public class ProductsController {

    @NonNull
    private ProductsService productsService;
    @NonNull
    private Mapper mapper;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public List<ProductDto> getProducts() {
        return mapper.map(productsService.getProducts(), ProductDto.class);
    }

}
