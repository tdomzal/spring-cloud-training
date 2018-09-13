package pl.training.cloud.store.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private long price;
    private int quantity;

}

