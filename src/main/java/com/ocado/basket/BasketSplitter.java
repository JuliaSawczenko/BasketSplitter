package com.ocado.basket;

import com.ocado.basket.exception.ConfigLoaderException;
import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.model.Product;
import com.ocado.basket.service.ConfigLoader;
import com.ocado.basket.service.DeliveryMethodService;
import com.ocado.basket.service.DeliveryOptimizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasketSplitter {

    private final DeliveryOptimizer deliveryOptimizer;

    // Dependency Injection constructor - for testability and flexibility
    public BasketSplitter(DeliveryOptimizer deliveryOptimizer) {
        this.deliveryOptimizer = deliveryOptimizer;
    }

    public BasketSplitter(String absolutePathToConfigFile) throws ConfigLoaderException {
        this(createDeliveryOptimizerFromConfig(absolutePathToConfigFile));
    }

    // Helper method
    private static DeliveryOptimizer createDeliveryOptimizerFromConfig(String absolutePathToConfigFile) throws ConfigLoaderException {
        var deliveryMethodService = new DeliveryMethodService(ConfigLoader.loadConfig(absolutePathToConfigFile));
        return new DeliveryOptimizer(deliveryMethodService);
    }

    public Map<String, List<String>> split(List<String> items) throws InvalidProductException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The list of items cannot be null or empty.");
        }

        // Transforms DeliveryMethod and List<Product> to Strings.
        // This simplifies the structure of the API response and helps to meet the requirements.
        return deliveryOptimizer.getDeliveryGroups(items).entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().stream().map(Product::name).collect(Collectors.toList())
                ));

    }
}
