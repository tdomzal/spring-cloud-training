package pl.training.cloud.store.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private long price;
    private int quantity;

    private boolean isAvailable() {
        return quantity > 0;
    }

    public void reserve() {
        if (isAvailable()) {
            quantity--;
        } else {
            throw new IllegalStateException();
        }
    }

    public void release() {
        quantity++;
    }

}
