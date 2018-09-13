package pl.training.cloud.store.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.training.cloud.store.dto.PaymentStatusDto;
import pl.training.cloud.store.model.OrderRequest;

@FeignClient("payments-microservice")
public interface PaymentsService {

    @RequestMapping(value = "payments", method = RequestMethod.POST)
    PaymentStatusDto pay(@RequestBody OrderRequest orderRequest);

}
