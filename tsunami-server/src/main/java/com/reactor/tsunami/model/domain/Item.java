package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private String productId;
    private String name;
    private Category category;
    private String imageUrl;
    private int quantity;
}
