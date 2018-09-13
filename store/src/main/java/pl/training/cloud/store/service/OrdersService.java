package pl.training.cloud.store.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.training.cloud.store.dto.PaymentStatusDto;
import pl.training.cloud.store.model.Order;
import pl.training.cloud.store.model.OrderRequest;
import pl.training.cloud.store.model.Product;
import pl.training.cloud.store.repository.OrdersRepository;

import static pl.training.cloud.store.model.OrderStatus.*;

@RequiredArgsConstructor
@Service
public class OrdersService {

    @NonNull
    private OrdersRepository ordersRepository;
    @NonNull
    private ProductsService productsService;
    @NonNull
    private PaymentsService paymentsService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(OrderRequest orderRequest) {
        Product product = productsService.reserveProduct(orderRequest.getProductId());
        orderRequest.setAmount(product.getPrice());
        Order order = new Order();
        order.setProduct(product);
        return ordersRepository.saveAndFlush(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order processOrder(OrderRequest orderRequest, Order order) {
        PaymentStatusDto paymentStatusDto = paymentsService.pay(orderRequest);
        if (!"CANCELED".equalsIgnoreCase(paymentStatusDto.getStatus())) {
            order.setTransactionId(paymentStatusDto.getTransactionId());
            order.setStatus(IN_PROGRESS);
        } else {
            order.setStatus(REJECTED);
        }
        return ordersRepository.saveAndFlush(order);
    }

    @StreamListener(Sink.INPUT)
    public void onPaymentStatusUpdate(PaymentStatusDto paymentStatusDto) {
        ordersRepository.getByTransactionId(paymentStatusDto.getTransactionId())
                .ifPresent(order -> updateOrder(paymentStatusDto, order));
    }

    private void updateOrder(PaymentStatusDto paymentStatusDto, Order order) {
        switch (paymentStatusDto.getStatus()) {
            case "COMPLETED":
                order.setStatus(COMPLETED);
                break;
            case "CANCELED":
                order.setStatus(CANCELED);
                productsService.releaseProduct(order.getProduct().getId());
                break;
        }
        ordersRepository.saveAndFlush(order);
    }

}
