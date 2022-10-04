package com.reactor.tsunami.model.repository;

import com.mongodb.Function;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.reactor.tsunami.model.domain.Page;
import com.reactor.tsunami.model.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.mongodb.client.model.Filters.*;

@Repository
@Slf4j
public class ProductFilterRepository {
    private static final String PRICE_FIELD = "price";
    private static final String MIN_PRICE = "minPrice";
    private static final String MAX_PRICE = "maxPrice";
    private static final String CATEGORY_FIELD = "category";
    private static final String NAME = "name";
    private static final String INVALID_QUERY = "invalid query params";
    private final MongoClient mongoClient;

    public ProductFilterRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Mono<Page<Product>> filterByParams(Map<String, String> params, int page, int pageSize) {

        AtomicLong totalSize = new AtomicLong();
        Bson[] query;
        final var currentPage = page;

        try {
            query = constructQuery(params);
        } catch (RuntimeException e) {
            log.info(INVALID_QUERY);
            throw new RuntimeException(INVALID_QUERY);
        }
//          TODO find why typed collection doesn't work
//        var productCollection = mongoClient.getDatabase("tsunami").getCollection("products", Product.class);
//
//        Function<MongoCollection<Product>, Publisher<Long>> countDocuments = query.length > 0 ? c -> c.countDocuments(and(query)) : MongoCollection::countDocuments;
//        Function<MongoCollection<Product>, FindPublisher<Product>> findQuery = query.length > 0 ? c -> c.find(and(query)) : MongoCollection::find;

        var documentCollection = mongoClient.getDatabase("tsunami").getCollection("products");

        Function<MongoCollection<Document>, Publisher<Long>> countDocuments = query.length > 0 ? c -> c.countDocuments(and(query)) : MongoCollection::countDocuments;
        Function<MongoCollection<Document>, FindPublisher<Document>> findQuery = query.length > 0 ? c -> c.find(and(query)) : MongoCollection::find;

        return Mono.from(countDocuments.apply(documentCollection))
                .doOnSuccess(totalSize::set)
                .thenMany(findQuery.apply(documentCollection)
                        .skip(--page * pageSize)
                        .limit(pageSize))
                .flatMap(document -> Mono.just(Product.builder()
                        .id(document.getObjectId("_id").toHexString())
                        .name(document.getString(NAME))
                        .price(document.getDouble(PRICE_FIELD))
                        .build()))
                .collectList()
                .map(products -> Page.of(currentPage, pageSize, totalSize.get(), products.size(), products));
    }

    private Bson[] constructQuery(Map<String, String> params) {
        List<Bson> bsonList = new ArrayList<>();

        if (params.containsKey(NAME))
            bsonList.add(regex("name", params.get(NAME), "i"));

        if (params.containsKey(MIN_PRICE)) {
            bsonList.add(gte(PRICE_FIELD, Double.parseDouble(params.get(MIN_PRICE))));
        }

        if (params.containsKey(MAX_PRICE)) {
            bsonList.add(lte(PRICE_FIELD, Double.parseDouble(params.get(MAX_PRICE))));
        }

        if (params.containsKey(CATEGORY_FIELD)) {
            bsonList.add(all(CATEGORY_FIELD, params.get(CATEGORY_FIELD)));
        }

        return bsonList.toArray(new Bson[0]);
    }

}
