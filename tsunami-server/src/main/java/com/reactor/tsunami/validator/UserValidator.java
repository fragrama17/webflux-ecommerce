package com.reactor.tsunami.validator;

import com.reactor.tsunami.model.domain.UserDTO;
import com.reactor.tsunami.exception.DTOValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidator{

    public UserDTO validate(UserDTO userDTO) {

        if (ObjectUtils.anyNull(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword()))
            throw new DTOValidationException("missing fields in user-DTO");

        if (!userDTO.getUsername().matches("[/\\w]{5,20}"))
            throw new DTOValidationException("username must be between 5 and 20 chars");

        if (!userDTO.getEmail().matches(".{3,50}@[/\\w]{3,50}\\.[/\\w]{2,3}"))
            throw new DTOValidationException("bad email");

        if (!userDTO.getPassword().matches(".{8,50}"))
            throw new DTOValidationException("password must be between 8 and 50 chars");

        return userDTO;
    }
}
