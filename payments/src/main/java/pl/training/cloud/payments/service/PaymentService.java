package pl.training.cloud.payments.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.cloud.payments.dto.PaymentStatusDto;
import pl.training.cloud.payments.model.Payment;
import pl.training.cloud.payments.model.PaymentInfo;
import pl.training.cloud.payments.model.PaymentStatus;
import pl.training.cloud.payments.repository.PaymentsRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static pl.training.cloud.payments.model.PaymentStatus.*;

@Log
@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {

    private static final long MAX_AMOUNT = 1_000;

    @NonNull
    private PaymentsRepository paymentsRepository;
    @NonNull
    private Source source;

    public String pay(PaymentInfo paymentInfo) {
        Payment payment = Payment.builder()
                .status(IN_PROGRESS)
                .startTimestamp(new Date())
                .transactionId(UUID.randomUUID().toString())
                .paymentInfo(paymentInfo)
                .build();
        paymentsRepository.save(payment);
        log.info("Payment created: " + payment);
        return payment.getTransactionId();
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void process() {
        paymentsRepository.getByStatus(IN_PROGRESS).forEach(this::process);
    }

    private void process(Payment payment) {
        PaymentInfo paymentInfo = payment.getPaymentInfo();
        if (paymentInfo.getAmount() > MAX_AMOUNT) {
            payment.setStatus(CANCELED);
        } else {
            payment.setStatus(COMPLETED);
        }
        paymentsRepository.save(payment);
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto(payment.getTransactionId(), payment.getStatus().name());
        log.info("Sending payment status: " + paymentStatusDto);
        source.output().send(MessageBuilder.withPayload(paymentStatusDto).build());
    }

    public Payment getPaymentStatus(String transactionId) {
        return paymentsRepository.getByTransactionId(transactionId)
                .orElseThrow(IllegalArgumentException::new);
    }

}
