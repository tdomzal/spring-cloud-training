package pl.training.cloud.users.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.training.cloud.users.model.Department;

@FeignClient("departments-microservice")
public interface FeignDepartmentsClient {

    @RequestMapping(method = RequestMethod.GET, value = "departments/{id}")
    Department getDepartmentById(@PathVariable("id") Long id);

}
