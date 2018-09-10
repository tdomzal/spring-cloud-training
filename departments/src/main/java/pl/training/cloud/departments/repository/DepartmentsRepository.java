package pl.training.cloud.departments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.cloud.departments.model.Department;

import java.util.Optional;

public interface DepartmentsRepository extends JpaRepository<Department, Long> {

    Optional<Department> getById(Long id);

}
