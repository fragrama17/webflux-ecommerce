package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("orders")
public class Order {
    @Id
    private String id;
    private String deliveryAddress;
    private PaymentOption paymentOption;
    private String userId;
    private List<Item> items;
    private Double totalPrice;
    private String notes;
    private LocalDateTime timeStampInsert;
}
