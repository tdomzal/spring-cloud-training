package pl.training.cloud.users.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
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
public class BalancedDepartmentsService implements DepartmentsService {

    private static final String RESOURCE_URI = "http://departments-microservice/departments/";

    @NonNull
    private RestTemplate restTemplate;

    @Cacheable(value = "departments", unless = "#result == null")
    @Override
    public Optional<String> getDepartmentName(Long departmentId) {
        try {
            DepartmentDto departmentDto = restTemplate.getForObject(RESOURCE_URI + departmentId, DepartmentDto.class);
            if (departmentDto != null) {
                log.warning("Fetching department...");
                return Optional.of(departmentDto.getName());
            }
        } catch (HttpClientErrorException ex) {
            log.warning("Fetching department with id " + departmentId + " failed");
        }
        return Optional.empty();
    }

}
