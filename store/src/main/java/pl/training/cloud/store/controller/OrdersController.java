package pl.training.cloud.store.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.training.cloud.store.dto.OrderRequestDto;
import pl.training.cloud.store.model.Mapper;
import pl.training.cloud.store.model.Order;
import pl.training.cloud.store.model.OrderRequest;
import pl.training.cloud.store.service.OrdersService;

import java.net.URI;

@RequestMapping("store")
@RequiredArgsConstructor
@RestController
public class OrdersController {

    @NonNull
    private OrdersService ordersService;
    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(value = "orders", method = RequestMethod.POST)
    public ResponseEntity placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequest orderRequest = mapper.map(orderRequestDto, OrderRequest.class);
        Order order = ordersService.placeOrder(orderRequest);
        URI uri = uriBuilder.requestUriWithId(order.getTransactionId());
        return ResponseEntity.created(uri).build();
    }

}
