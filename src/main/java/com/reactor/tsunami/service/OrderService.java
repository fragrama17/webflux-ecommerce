package com.reactor.tsunami.service;

import com.reactor.tsunami.model.domain.Item;
import com.reactor.tsunami.model.domain.Order;
import com.reactor.tsunami.model.domain.OrderDTO;
import com.reactor.tsunami.model.domain.Page;
import com.reactor.tsunami.model.repository.OrderRepository;
import com.reactor.tsunami.model.repository.ProductRepository;
import com.reactor.tsunami.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Mono<Page<Order>> getPagedOrders(String userId, Integer page, Integer pageSize) {
        AtomicLong size = new AtomicLong();
        final var currentPage = page;

        return orderRepository.count()
                .doOnSuccess(size::set)
                .thenMany(orderRepository.findByUserId(userId, --page * pageSize, pageSize))
                .collectList()
                .flatMap(orders -> Mono.just(Page.of(currentPage, pageSize, size.get(), orders.size(), orders)));
    }

    public Mono<Order> createOrder(String userId, OrderDTO orderDTO) {
        var itemsMap = new HashMap<String, Item>();
        var productsPriceMap = new HashMap<String, BigDecimal>();

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(() -> new RuntimeException("user-id not found for this order")))
                .thenMany(productRepository.findAllById(
                        orderDTO.getItems()
                                .stream()
                                .map(item -> {
                                    itemsMap.put(item.getProductId(), item);
                                    return item.getProductId();
                                })
                                .distinct()
                                .collect(Collectors.toList()))
                )
                .map(product -> {
                    productsPriceMap.put(product.getId(),
                            BigDecimal.valueOf(product.getPrice())
                                    .multiply(BigDecimal.valueOf(itemsMap.get(product.getId()).getQuantity())));

                    var newAvailability = product.getAvailability() - itemsMap.get(product.getId()).getQuantity();
                    if (newAvailability < 0)
                        throw new RuntimeException("order not valid, quantity is greater then availability");

                    product.setTimeStampUpdate(LocalDateTime.now());
                    product.setAvailability(newAvailability);
                    return product;
                })
                .collectList()
                .filter(products -> products.size() == itemsMap.size())
                .switchIfEmpty(Mono.error(() -> new RuntimeException("nr. of items different from nr. of products")))
                .flatMapMany(productRepository::saveAll)
                .collectList()
                .flatMap(products -> orderRepository.save(
                        Order.builder()
                                .userId(userId)
                                .deliveryAddress(orderDTO.getDeliveryAddress())
                                .items(products.stream()
                                        .map(product -> Item.builder()
                                                .productId(product.getId())
                                                .name(product.getName())
                                                .category(product.getCategory())
                                                .quantity(itemsMap.get(product.getId()).getQuantity())
                                                .imageUrl(product.getImageUrl())
                                                .build())
                                        .collect(Collectors.toList()))
                                .paymentOption(orderDTO.getPaymentOption())
                                .totalPrice(productsPriceMap.values()
                                        .stream()
                                        .reduce(BigDecimal::add)
                                        .orElseThrow(() -> new RuntimeException("total-price can't be null !"))
                                        .setScale(2, RoundingMode.UP).doubleValue())
                                .notes(orderDTO.getNotes())
                                .timeStampInsert(LocalDateTime.now())
                                .build()
                ))
                .log();
    }

}
