package com.reactor.tsunami.model.repository;

import com.reactor.tsunami.model.domain.Admin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AdminRepository extends ReactiveMongoRepository<Admin, String> {

    Mono<Admin> findAdminByUserId(String userId);
}
