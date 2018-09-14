package pl.training.cloud.users.service;

import pl.training.cloud.users.model.Department;

import java.util.Optional;

public interface DepartmentsService {

    Optional<Department> getDepartmentById(Long id);

}
