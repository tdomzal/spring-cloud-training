package pl.training.cloud.store.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.cloud.store.model.Product;
import pl.training.cloud.store.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Log
@Transactional
@RequiredArgsConstructor
@Service
public class ProductsService {

    @NonNull
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product reserveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        product.reserve();
        return product;
    }

}
