package pl.training.cloud.store.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.store.dto.OrderRequestDto;
import pl.training.cloud.store.model.Mapper;
import pl.training.cloud.store.model.Order;
import pl.training.cloud.store.model.OrderRequest;
import pl.training.cloud.store.service.StoreService;

import java.net.URI;

@RequestMapping("store")
@RequiredArgsConstructor
@RestController
public class OrdersController {

    @NonNull
    private StoreService storeService;
    @NonNull
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(value = "orders", method = RequestMethod.POST)
    public ResponseEntity placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequest orderRequest = mapper.map(orderRequestDto, OrderRequest.class);
        Order order = storeService.placeOrder(orderRequest);
        URI uri = uriBuilder.requestUriWithId(order.getTransactionId());
        return ResponseEntity.created(uri).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

}
