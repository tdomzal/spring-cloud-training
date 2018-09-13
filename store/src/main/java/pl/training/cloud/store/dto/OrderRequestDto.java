package pl.training.cloud.store.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String cardNumber;
    private String ccv;
    private long expirationDate;
    private Long productId;

}
