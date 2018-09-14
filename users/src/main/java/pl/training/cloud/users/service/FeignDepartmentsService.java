package pl.training.cloud.users.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.model.Department;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10")
        }
)
@Service
public class FeignDepartmentsService implements DepartmentsService {

    private FeignDepartmentsClient feignDepartmentsClient;

    @Autowired
    public FeignDepartmentsService(FeignDepartmentsClient feignDepartmentsClient) {
        this.feignDepartmentsClient = feignDepartmentsClient;
    }

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
    public Optional<Department> getDepartmentById(Long id) {
        try {
            return Optional.of(feignDepartmentsClient.getDepartmentById(id));
        } catch (FeignException ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "### Fetching department failed");
        }
        return Optional.empty();
    }

}
