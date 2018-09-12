package pl.training.cloud.users.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10")
        }
)
@Log
@RequiredArgsConstructor
@Service
public class FeignDepartmentsService implements DepartmentsService {

    @NonNull
    private FeignDepartmentsClient departmentsClient;

    @HystrixCommand(
            threadPoolKey = "departments",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "15")
            },
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    //@Cacheable(value = "departments", unless = "#result == null")
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
