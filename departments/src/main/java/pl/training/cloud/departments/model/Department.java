package pl.training.cloud.departments.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Department {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;

}
