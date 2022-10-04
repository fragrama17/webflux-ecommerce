package com.reactor.tsunami.validator;

import com.reactor.tsunami.model.domain.ProductDTO;
import com.reactor.tsunami.exception.DTOValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    public ProductDTO validate(ProductDTO productDTO) {

        if (StringUtils.isEmpty(productDTO.getName()) || StringUtils.isEmpty(productDTO.getPrice().toString()))
            throw new DTOValidationException("missing fields in product-DTO");

        if (productDTO.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new DTOValidationException("price can't be negative");

        return productDTO;
    }
}
