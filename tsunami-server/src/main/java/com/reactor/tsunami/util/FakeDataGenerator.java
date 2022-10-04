package com.reactor.tsunami.util;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.reactor.tsunami.model.domain.Category;
import com.reactor.tsunami.model.domain.Product;
import com.reactor.tsunami.model.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FakeDataGenerator {

    public static void main(String[] args) throws IOException {

        new FileSystemResource("products.json").getOutputStream()
                .write(new Gson().toJson(generateFakeProducts(2_000_000)).getBytes(StandardCharsets.UTF_8));

        new FileSystemResource("users.json").getOutputStream()
                .write(new Gson().toJson(generateFakeUsers(100_000)).getBytes(StandardCharsets.UTF_8));
    }

    private static final Faker faker = new Faker(Locale.ENGLISH);

    public static User generateFakeUser() {
        String username = faker.name().username();
        return User.builder()
                .username(username)
                .email(faker.internet().emailAddress(username))
                .password(faker.internet().password())
                .build();
    }

    public static Product generateFakeProduct() {
        Category[] categories = Category.values();
        var priceString = faker.commerce().price();

        return Product.builder()
                .name(faker.commerce().productName())
                .category(categories[faker.random().nextInt(categories.length)])
                .price(new BigDecimal(priceString.substring(0, priceString.length() - 3))
                        .add(BigDecimal.valueOf(0.99)).setScale(2).doubleValue())
                .availability(faker.random().nextInt(100))
                .imageUrl(faker.internet().image())
                .build();
    }

    public static List<User> generateFakeUsers(int size) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            users.add(generateFakeUser());
        }

        return users;
    }

    public static List<Product> generateFakeProducts(int size) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            products.add(generateFakeProduct());
        }

        return products;
    }

}
