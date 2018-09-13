package pl.training.cloud.payments.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class PaymentInfo {

    @GeneratedValue
    @Id
    private Long id;
    private String cardNumber;
    private String ccv;
    private long expirationDate;
    private long amount;

}
