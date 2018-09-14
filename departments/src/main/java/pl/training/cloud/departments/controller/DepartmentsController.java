package pl.training.cloud.departments.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.departments.model.Mapper;
import pl.training.cloud.departments.dto.DepartmentDto;
import pl.training.cloud.departments.model.Department;
import pl.training.cloud.departments.service.DepartmentNotFoundException;
import pl.training.cloud.departments.service.DepartmentsService;

import java.net.URI;
import java.util.Locale;

@Api(description = "Departments resource")
@RequestMapping("departments")
@RestController
public class DepartmentsController {

    private DepartmentsService departmentsService;
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @Autowired
    public DepartmentsController(DepartmentsService departmentsService, Mapper mapper) {
        this.departmentsService = departmentsService;
        this.mapper = mapper;
    }

    @ApiOperation("Create new department")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentsService.addDepartment(dtoToModel(departmentDto));
        URI newDepartmentUri = uriBuilder.requestUriWithId(department.getId());
        return ResponseEntity.created(newDepartmentUri).body(modelToDto(department));
    }

    @ApiOperation(value = "Get department by id", response = DepartmentDto.class)
    @RequestMapping(value = "{department-id}", method = RequestMethod.GET)
    public DepartmentDto getDepartmentById(@PathVariable("department-id") Long id) {
        Department department = departmentsService.getDepartmentById(id);
        return modelToDto(department);
    }

    @ApiOperation(value = "Update department")
    @RequestMapping(value = "{department-id}", method = RequestMethod.PUT)
    public ResponseEntity updateDepartment(@PathVariable("department-id") Long id,
                                           @RequestBody DepartmentDto departmentDto) {
        Department department = dtoToModel(departmentDto);
        department.setId(id);
        departmentsService.updateDepartment(department);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete department")
    @RequestMapping(value = "{department-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable("department-id") Long id) {
        departmentsService.deleteDepartmentWithId(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity onOrganizationNotFound(DepartmentNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(mapper.map(ex, locale), HttpStatus.NOT_FOUND);
    }

    private DepartmentDto modelToDto(Department department) {
        return mapper.map(department, DepartmentDto.class);
    }

    private Department dtoToModel(DepartmentDto departmentDto) {
        return mapper.map(departmentDto, Department.class);
    }

}
