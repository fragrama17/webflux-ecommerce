package com.reactor.tsunami.model.domain;

import java.util.Arrays;

public enum Category {
    SURF_BOARD,
    SURF_SKATE,
    CLOTHING,
    ACCESSORY,
    UNKNOWN;

    public static Category fromValue(String value) {
        return Arrays.stream(Category.values())
                .filter(c -> c.name().equalsIgnoreCase(value))
                .findAny()
                .orElse(UNKNOWN);
    }
}
