package pl.training.cloud.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.users.model.Department;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AutoBalancedDepartmentsService implements DepartmentsService {

    private static final String RESOURCE_URI = "http://departments-microservice/departments/";

    private RestTemplate restTemplate;

    @Autowired
    public AutoBalancedDepartmentsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "departments", unless = "#result == null")
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        try {
            return Optional.of(restTemplate.getForObject(RESOURCE_URI + id, Department.class));
        } catch (HttpClientErrorException ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "### Fetching department failed");
        }
        return Optional.empty();
    }

}
