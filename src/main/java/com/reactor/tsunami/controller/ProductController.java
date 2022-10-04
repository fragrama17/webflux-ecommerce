package com.reactor.tsunami.controller;

import com.reactor.tsunami.model.domain.Page;
import com.reactor.tsunami.model.domain.Product;
import com.reactor.tsunami.model.domain.ProductDTO;
import com.reactor.tsunami.service.ProductService;
import com.reactor.tsunami.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;

    public ProductController(ProductService productService, ProductValidator productValidator) {
        this.productService = productService;
        this.productValidator = productValidator;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Page<Product>> getProducts(@RequestParam Map<String, String> searchParams) {

        var page = Integer.parseInt(searchParams.getOrDefault("page", "1"));
        var pageSize = Integer.parseInt(searchParams.getOrDefault("pageSize", "1000"));

        if (page <= 0) page = 1;
        if (pageSize <= 0 || pageSize > 1000) pageSize = 1000;

        return productService.getPagedProducts(searchParams, page, pageSize).log();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productValidator.validate(productDTO)).log();
    }

    @PutMapping(path = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Product>> modifyProduct(@PathVariable String productId, @RequestBody ProductDTO productDTO) {
        return productService.modifyProduct(productId, productValidator.validate(productDTO))
                .map(p -> ResponseEntity.of(Optional.of(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping(path = "/{productId}")
    public Mono<Void> deleteProduct(@PathVariable String productId) {
        return productService.deleteProduct(productId).log();
    }

}
