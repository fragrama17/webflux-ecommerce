package com.reactor.tsunami.model.repository;

import com.reactor.tsunami.model.domain.ProductsCache;
import org.springframework.data.repository.CrudRepository;

public interface ProductCacheRepository extends CrudRepository<ProductsCache, String> {
}
