package com.reactor.tsunami.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class AccessRecordSerializer implements Serializer<UserAccessRecord> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, UserAccessRecord userAccessRecord) {

        try {

            return objectMapper.writeValueAsBytes(userAccessRecord);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }

    }
}
