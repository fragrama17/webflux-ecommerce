package com.reactor.tsunami.model.repository;


import com.reactor.tsunami.model.domain.TokenHash;
import org.springframework.data.repository.CrudRepository;

public interface TokenStoreRepository extends CrudRepository<TokenHash, String> {

}
