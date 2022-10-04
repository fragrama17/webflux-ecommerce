package com.reactor.tsunami.model.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<T> {
    private int currentPage;
    private int pageSize;
    private long totalSize;
    private int contentSize;
    private List<T> content;

    public static <T> Page<T> emptyPage() {
        return new Page<>(0, 0, 0, 0, new ArrayList<>());
    }

    public static <T> Page<T> of(int currentPage, int pageSize, long totalSize, int contentSize, List<T> content) {
        return new Page<>(currentPage, pageSize, totalSize, contentSize, content);
    }
}
