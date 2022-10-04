package com.reactor.tsunami.controller;

import com.reactor.tsunami.model.domain.Order;
import com.reactor.tsunami.model.domain.OrderDTO;
import com.reactor.tsunami.model.domain.Page;
import com.reactor.tsunami.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Page<Order>> getOrders(@RequestHeader String user,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize) {
        if (page == null || page <= 0) page = 1;
        if (pageSize == null || pageSize <= 0) pageSize = 10;

        return orderService.getPagedOrders(user, page, pageSize);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(@RequestHeader String user, @RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(user, orderDTO);
    }

}
