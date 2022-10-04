package com.reactor.tsunami.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@Slf4j
public class KafkaService {

    private final KafkaTemplate<String, UserAccessRecord> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, UserAccessRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void logUserAccess(String userId, boolean isValidAccess, String timestamp) {

        var accessRecord = UserAccessRecord.builder()
                .userId(userId)
                .validAccess(isValidAccess)
                .timestamp(timestamp).build();

        var future = kafkaTemplate.send(new ProducerRecord<>(
                KafkaTopicConfig.USER_ACCESS_TOPIC,
                UUID.randomUUID().toString(),
                accessRecord
        ));

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(@Nullable SendResult<String, UserAccessRecord> result) {
                log.info("Sent message=[" + accessRecord +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=["
                        + accessRecord + "] due to : " + ex.getMessage());
            }
        });

    }

}
