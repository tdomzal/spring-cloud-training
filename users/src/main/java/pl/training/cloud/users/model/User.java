package pl.training.cloud.users.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
public class User {

    @GeneratedValue
    @Id
    private Long id;
    private String firstName;
    private String lastName;

}