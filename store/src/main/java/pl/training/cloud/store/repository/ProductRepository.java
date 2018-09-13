package pl.training.cloud.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
