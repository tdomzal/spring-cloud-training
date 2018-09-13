package pl.training.cloud.store.model;

import lombok.Data;

@Data
public class OrderRequest {

    private String cardNumber;
    private String ccv;
    private long expirationDate;
    private Long productId;
    private long amount;

}
