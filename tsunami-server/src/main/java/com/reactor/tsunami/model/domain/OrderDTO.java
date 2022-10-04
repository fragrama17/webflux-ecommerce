package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String deliveryAddress;
    private PaymentOption paymentOption;
    private List<Item> items;
    private String notes;
}
