package pl.training.cloud.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.payments.model.Payment;
import pl.training.cloud.payments.model.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {

    List<Payment> getByStatus(PaymentStatus paymentStatus);

    Optional<Payment> getByTransactionId(String transactionId);

}
