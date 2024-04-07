package com.ocado.basket.mocks;

import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;

import java.util.List;
import java.util.Map;

public class MockedValues {

    public static final List<String> PRODUCTS = List.of("Fond - Chocolate", "Chocolate - Unsweetened");

    public static final Map<Product, List<DeliveryMethod>> PRODUCTS_WITH_DELIVERY_METHODS = Map.of(
            new Product("Fond - Chocolate"), List.of(new DeliveryMethod("Pick-up point"), new DeliveryMethod("Express Collection"), new DeliveryMethod("Mailbox delivery")),
            new Product("Chocolate - Unsweetened"), List.of(new DeliveryMethod("Pick-up point")));

    public static final Map<String, List<String>> EXPECTED_GROUPS_STRING = Map.of(
            "Pick-up point", List.of("Chocolate - Unsweetened", "Fond - Chocolate"));

    public static final Map<DeliveryMethod, List<Product>> EXPECTED_GROUPS = Map.of(
            new DeliveryMethod("Pick-up point"), List.of(new Product("Chocolate - Unsweetened"), new Product("Fond - Chocolate")));
}
