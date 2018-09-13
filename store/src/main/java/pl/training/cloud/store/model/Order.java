package pl.training.cloud.store.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "orders")
@Entity
@Data
public class Order {

    @GeneratedValue
    @Id
    private Long id;
    private String transactionId;
    @ManyToOne
    private Product product;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
