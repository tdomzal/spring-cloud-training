package pl.training.cloud.users.service;

import feign.FeignException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.dto.DepartmentDto;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class FeignDepartmentsService implements DepartmentsService {

    @NonNull
    private FeignDepartmentsClient departmentsClient;

    @Cacheable(value = "departments", unless = "#result == null")
    @Override
    public Optional<String> getDepartmentName(Long departmentId) {
        try {
            DepartmentDto departmentDto = departmentsClient.getDepartment(departmentId);
            if (departmentDto != null) {
                log.warning("Fetching department...");
                return Optional.of(departmentDto.getName());
            }
        } catch (FeignException ex) {
            log.warning("Fetching department with id " + departmentId + " failed");
        }
        return Optional.empty();
    }

    @CacheEvict(value = "departments", allEntries = true)
    @StreamListener(Sink.INPUT)
    public void onDepartmentUpdate(String message) {
        System.out.println("Cleaning departments cache");
    }

}
