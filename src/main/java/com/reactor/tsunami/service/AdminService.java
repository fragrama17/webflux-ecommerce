package com.reactor.tsunami.service;

import com.reactor.tsunami.model.domain.Admin;
import com.reactor.tsunami.exception.UnauthorizedException;
import com.reactor.tsunami.model.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Mono<Admin> checkIfAdmin(String username) {
        return adminRepository.findAdminByUserId(username)
                .switchIfEmpty(Mono.error(() -> new UnauthorizedException("must be admin for create, modify and delete a product")));
    }
}
