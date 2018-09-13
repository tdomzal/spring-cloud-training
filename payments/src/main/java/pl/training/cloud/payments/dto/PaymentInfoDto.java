package pl.training.cloud.payments.dto;

import lombok.Data;

@Data
public class PaymentInfoDto {

    private String cardNumber;
    private String ccv;
    private long expirationDate;
    private long amount;

}