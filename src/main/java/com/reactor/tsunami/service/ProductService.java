package com.reactor.tsunami.service;

import com.reactor.tsunami.exception.ProductNotFoundException;
import com.reactor.tsunami.model.domain.*;
import com.reactor.tsunami.model.repository.ProductCacheRepository;
import com.reactor.tsunami.model.repository.ProductFilterRepository;
import com.reactor.tsunami.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductFilterRepository productFilterRepository;
    private final ProductCacheRepository productCacheRepository;

    public Mono<Page<Product>> getPagedProducts(Map<String, String> parametersMap, int page, int pageSize) {

        return getCachedProducts(parametersMap)
                .filter(pp -> !CollectionUtils.isEmpty(pp.getContent()))
                .switchIfEmpty(saveCacheProductsQuery(
                        productFilterRepository.filterByParams(parametersMap, page, pageSize),
                        parametersMap.toString()));
    }

    private Mono<Page<Product>> getCachedProducts(Map<String, String> parametersMap) {
        return Mono.just(productCacheRepository.findById(parametersMap.toString())  // blocking on memory(no disk !)-> no starvation
                .map(ProductsCache::getPagedProducts)
                .filter(pp -> !CollectionUtils.isEmpty(pp.getContent()))
                .orElse(Page.emptyPage()));
    }

    private Mono<Page<Product>> saveCacheProductsQuery(Mono<Page<Product>> pagedProducts, String queryId) {
        return pagedProducts
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(pp -> productCacheRepository.save(ProductsCache.builder()
                        .id(queryId)
                        .pagedProducts(pp)
                        .build()));
    }


    public Mono<Product> addProduct(ProductDTO productDTO) {
        return productRepository.save(Product.builder()
                .name(productDTO.getName())
                .category(Category.fromValue(productDTO.getCategory()))
                .price(productDTO.getPrice().setScale(2, RoundingMode.UP).doubleValue())
                .timeStampInsert(LocalDateTime.now())
                .availability(productDTO.getAvailability())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .build()
        );
    }

    public Mono<Product> modifyProduct(String productId, ProductDTO productDTO) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(() -> new ProductNotFoundException("product not found")))
                .flatMap(p -> {
                    p.setName(productDTO.getName());
                    p.setDescription(productDTO.getDescription());
                    p.setCategory(Category.fromValue(productDTO.getCategory()));
                    p.setPrice(productDTO.getPrice().setScale(2, RoundingMode.UP).doubleValue());
                    p.setImageUrl(productDTO.getImageUrl());
                    p.setAvailability(productDTO.getAvailability());
                    p.setTimeStampUpdate(LocalDateTime.now());
                    return productRepository.save(p);
                });
    }

    public Mono<Void> deleteProduct(String productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(() -> new ProductNotFoundException("product not found")))
                .then(productRepository.deleteById(productId));
    }

}
