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
public class BalancedDepartmentsService implements DepartmentsService {

    private static final String DEPARTMENTS_MICROSERVICE = "departments-microservice";
    private static final String RESOURCE_NAME = "/departments/";

    private DiscoveryClient discoveryClient;

    @Autowired
    public BalancedDepartmentsService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Cacheable(value = "departments", unless = "#result == null")
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        Optional<String> resourceUri = getResourceUri(id);
        if (!resourceUri.isPresent()) {
            return Optional.empty();
        }
        return getDepartment(resourceUri.get());
    }

    private Optional<Department> getDepartment(String resourceUri) {
        try {
            Optional.of(new RestTemplate().getForObject(resourceUri, Department.class));
        } catch (HttpClientErrorException ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "### Fetching department failed");
        }
        return Optional.empty();
    }

    private Optional<String> getResourceUri(Long departmentId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(DEPARTMENTS_MICROSERVICE);
        if (serviceInstances.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceInstances.get(0).getUri().toString() + RESOURCE_NAME + departmentId);
    }

}
