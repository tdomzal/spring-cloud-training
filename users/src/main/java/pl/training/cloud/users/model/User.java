package pl.training.cloud.users.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class User {

    @GeneratedValue
    @Id
    private Long id;
    private String firstName;
    private String lastName;

}