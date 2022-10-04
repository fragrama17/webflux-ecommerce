package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private int availability;
    private String imageUrl;
}

