package ru.geekbrains.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entities.Product;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> filterByMinPrice(BigDecimal minPrice) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> filterByMaxPrice(BigDecimal maxPrice) {
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> filterByMinAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, builder) ->
                builder.between(root.get("price"), minPrice, maxPrice);
    }
}
