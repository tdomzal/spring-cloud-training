package pl.training.cloud.store.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.cloud.store.dto.PaymentStatusDto;
import pl.training.cloud.store.model.Order;
import pl.training.cloud.store.model.OrderRequest;
import pl.training.cloud.store.model.OrderStatus;
import pl.training.cloud.store.model.Product;
import pl.training.cloud.store.repository.OrdersRepository;

import static pl.training.cloud.store.model.OrderStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class OrdersService {

    @NonNull
    private OrdersRepository ordersRepository;
    @NonNull
    private ProductsService productsService;
    @NonNull
    private PaymentsService paymentsService;

    // todo: blokwanie entity i osobna transakcja obsługa canceled

    public Order placeOrder(OrderRequest orderRequest) {
        Product product = productsService.reserveProduct(orderRequest.getProductId());
        Order order = new Order();
        order.setProduct(product);
        ordersRepository.saveAndFlush(order);

        PaymentStatusDto paymentStatusDto = paymentsService.pay(orderRequest);
        if (!"CANCELED".equalsIgnoreCase(paymentStatusDto.getStatus())) {
            order.setTransactionId(paymentStatusDto.getTransactionId());
            order.setStatus(IN_PROGRESS);
        } else {
            order.setStatus(REJECTED);
        }
        ordersRepository.saveAndFlush(order);
        return order;
    }

}
