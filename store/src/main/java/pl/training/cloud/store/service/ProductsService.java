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
import java.util.function.Consumer;

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
        return process(productId, Product::reserve);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                    .orElseThrow(IllegalArgumentException::new);
    }

    public Product releaseProduct(Long productId) {
        return process(productId, Product::release);
    }

    private Product process(Long productId, Consumer<Product> callback) {
        Product product = getProduct(productId);
        callback.accept(product);
        productRepository.save(product);
        return product;
    }


}
