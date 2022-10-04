package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private Category category;
    private String description;
    private double price;
    private int availability;
    private String imageUrl;
    private LocalDateTime timeStampInsert;
    private LocalDateTime timeStampUpdate;
}
