package pl.training.cloud.departments.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.departments.dto.DepartmentDto;
import pl.training.cloud.departments.model.Department;
import pl.training.cloud.departments.model.Mapper;
import pl.training.cloud.departments.service.DepartmentsService;

import java.net.URI;

@RequestMapping("departments")
@RestController
@RequiredArgsConstructor
public class DepartmentsController {

    @NonNull
    private DepartmentsService departmentsService;
    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentsService.addDepartment(dtoToModel(departmentDto));
        URI newDepartmentUri = uriBuilder.requestUriWithId(department.getId());
        return ResponseEntity.created(newDepartmentUri).body(modelToDto(department));
    }

    @RequestMapping(value = "{department-id}", method = RequestMethod.GET)
    public DepartmentDto getDepartmentById(@PathVariable("department-id") Long id) {
        Department department = departmentsService.getDepartmentById(id);
        return modelToDto(department);
    }

    @RequestMapping(value = "{department-id}", method = RequestMethod.PUT)
    public ResponseEntity updateDepartment(@PathVariable("department-id") Long id, @RequestBody DepartmentDto departmentDto) {
        Department department = dtoToModel(departmentDto);
        department.setId(id);
        departmentsService.updateDepartment(department);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "{department-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable("department-id") Long id) {
        departmentsService.deleteDepartmentWithId(id);
        return ResponseEntity.noContent().build();
    }

    private DepartmentDto modelToDto(Department department) {
        return mapper.map(department, DepartmentDto.class);
    }

    private Department dtoToModel(DepartmentDto departmentDto) {
        return mapper.map(departmentDto, Department.class);
    }

}
