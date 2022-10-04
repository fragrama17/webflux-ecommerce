package com.reactor.tsunami.model.repository;

import com.reactor.tsunami.model.domain.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    @Aggregation(pipeline = {
            "{ '$skip' : ?0 }",
            "{ '$limit' : ?1 }"
    })
    Flux<Product> findBy(int skip, int limit);
}
