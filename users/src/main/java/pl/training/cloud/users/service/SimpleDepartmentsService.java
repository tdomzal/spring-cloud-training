package pl.training.cloud.users.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.users.dto.DepartmentDto;

import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class SimpleDepartmentsService implements DepartmentsService {

    private static final String DEPARTMENTS_MICROSERVICE = "departments-microservice";
    private static final String RESOURCE_PATH = "/departments/";

    @NonNull
    private DiscoveryClient discoveryClient;
    @NonNull
    private RestTemplate restTemplate;

    @Override
    public Optional<String> getDepartmentName(Long departmentId) {
        Optional<String> resourceUri = getResourceUri(departmentId);
        if (resourceUri.isPresent()) {
            return getDepartmentName(resourceUri.get());
        }
        return Optional.empty();
    }

    private Optional<String> getResourceUri(Long departmentId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(DEPARTMENTS_MICROSERVICE);
        if (serviceInstances.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceInstances.get(0).getUri().toString() + RESOURCE_PATH + departmentId);
    }

    private Optional<String> getDepartmentName(String resourceUri) {
        try {
            DepartmentDto departmentDto = restTemplate.getForObject(resourceUri, DepartmentDto.class);
            if (departmentDto != null) {
                return Optional.of(departmentDto.getName());
            }
        } catch (HttpClientErrorException ex) {
            log.warning("Fetching department failed " + resourceUri);
        }
        return Optional.empty();
    }

}
