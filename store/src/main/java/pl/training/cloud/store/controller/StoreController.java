package pl.training.cloud.store.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.cloud.store.dto.ProductDto;
import pl.training.cloud.store.model.Mapper;
import pl.training.cloud.store.service.StoreService;

import java.util.List;

@RequestMapping("store")
@RequiredArgsConstructor
@RestController
public class StoreController {

    @NonNull
    private StoreService storeService;
    @NonNull
    private Mapper mapper;

    @RequestMapping("products")
    public List<ProductDto> getProducts() {
        return mapper.map(storeService.getProducts(), ProductDto.class);
    }

}
