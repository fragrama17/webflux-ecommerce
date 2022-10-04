package com.reactor.tsunami.model.repository;

import com.reactor.tsunami.model.domain.Order;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {

    @Aggregation(pipeline = {
            "{ '$sort' :  { '_id' : -1 }}",
            "{ '$skip' : ?1 }",
            "{ '$limit' : ?2 }"
    }
    )
    Flux<Order> findByUserId(String userId, int skip, int limit);

}
