package pl.training.cloud.payments.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.payments.dto.PaymentInfoDto;
import pl.training.cloud.payments.dto.PaymentStatusDto;
import pl.training.cloud.payments.model.Mapper;
import pl.training.cloud.payments.model.PaymentInfo;
import pl.training.cloud.payments.service.PaymentService;

import java.net.URI;

@RequestMapping("payments")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    @NonNull
    private PaymentService paymentService;
    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity pay(@RequestBody PaymentInfoDto paymentInfoDto) {
        PaymentInfo paymentInfo = mapper.map(paymentInfoDto, PaymentInfo.class);
        String transactionId = paymentService.pay(paymentInfo);
        URI uri = uriBuilder.requestUriWithId(transactionId);
        return ResponseEntity.created(uri).build();
    }
    @RequestMapping(value = "{transaction-id}", method = RequestMethod.GET)
    public ResponseEntity getPaymentStatus(@PathVariable("transaction-id") String transactionId) {
        PaymentStatusDto paymentStatusDto = mapper.map(paymentService.getPaymentStatus(transactionId), PaymentStatusDto.class);
        return ResponseEntity.ok(paymentStatusDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity onPaymentNotFound() {
        return ResponseEntity.notFound().build();
    }

}
