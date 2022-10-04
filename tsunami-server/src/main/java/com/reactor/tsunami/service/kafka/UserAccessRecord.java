package com.reactor.tsunami.service.kafka;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccessRecord {
    String userId;
    boolean validAccess;
    String timestamp;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}



