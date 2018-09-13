package pl.training.cloud.store.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.training.cloud.store.model.Order;
import pl.training.cloud.store.model.OrderRequest;

@RequiredArgsConstructor
@Service
public class StoreService {

    @NonNull
    private OrdersService ordersService;

    public Order placeOrder(OrderRequest orderRequest) {
        Order order = ordersService.createOrder(orderRequest);
        ordersService.processOrder(orderRequest, order);
        return order;
    }

}
