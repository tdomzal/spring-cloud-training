package pl.training.cloud.departments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.training.cloud.departments.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentsRepository extends JpaRepository<Department, Long> {

    Optional<Department> getById(Long id);

    @Query("select d from Department d where d.name = :department_name")
    List<Department> getDepartments(@Param("department_name") String department);

}
