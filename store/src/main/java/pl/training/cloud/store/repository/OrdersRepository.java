package pl.training.cloud.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.store.model.Order;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Long> {

    Optional<Order> getByTransactionId(String transactionId);

}
