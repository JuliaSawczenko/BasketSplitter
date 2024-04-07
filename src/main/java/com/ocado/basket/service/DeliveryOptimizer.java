package com.ocado.basket.service;

import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;

import java.util.*;

public class DeliveryOptimizer {

    private final DeliveryMethodService deliveryMethodService;

    public DeliveryOptimizer(DeliveryMethodService deliveryMethodService) {
        this.deliveryMethodService = deliveryMethodService;
    }

    // Group products by delivery method based on the configuration.
    public Map<DeliveryMethod, List<Product>> getDeliveryGroups(List<String> productNames) {
        Map<DeliveryMethod, Set<Product>> deliveryMethodWithProducts = new HashMap<>();

        for (String productName : productNames) {
            Product product = new Product(productName);
            List<DeliveryMethod> deliveryMethods = deliveryMethodService.getDeliveryMethodsForProduct(product);

            for (DeliveryMethod method : deliveryMethods) {
                deliveryMethodWithProducts.computeIfAbsent(method, k -> new HashSet<>()).add(product);
            }
        }

        return filterOptimalDeliveryGroups(deliveryMethodWithProducts);

    }

    // Filter the initial groups of products to find an optimized solution.
    private Map<DeliveryMethod, List<Product>> filterOptimalDeliveryGroups(Map<DeliveryMethod, Set<Product>> deliveryMethodWithProducts) {
        Map<DeliveryMethod, Integer> ranking = new HashMap<>();
        for (DeliveryMethod method : deliveryMethodWithProducts.keySet()) {
            ranking.put(method, 0);
        }

        // Calculate ranking based on the number of products.
        for (var entry : deliveryMethodWithProducts.entrySet()) {
            ranking.put(entry.getKey(), entry.getValue().size());
        }

        Map<DeliveryMethod, List<Product>> optimizedGroups = new LinkedHashMap<>();
        Set<Product> groupedProducts = new HashSet<>();

        while (!ranking.isEmpty()) {
            DeliveryMethod maxMethod = Collections.max(ranking.entrySet(), Map.Entry.comparingByValue()).getKey();
            Set<Product> potentialGroup = new HashSet<>(deliveryMethodWithProducts.get(maxMethod));
            potentialGroup.removeIf(groupedProducts::contains); // Exclude already grouped products.

            if (!potentialGroup.isEmpty()) {
                optimizedGroups.put(maxMethod, new ArrayList<>(potentialGroup));
                groupedProducts.addAll(potentialGroup);

                ranking.remove(maxMethod);

                // Update the ranking to show the removal of grouped products.
                updateRankingAfterSelection(deliveryMethodWithProducts, ranking, groupedProducts);
            } else {
                ranking.remove(maxMethod);
            }
        }

        return optimizedGroups;
    }

    private void updateRankingAfterSelection(Map<DeliveryMethod, Set<Product>> deliveryMethodWithProducts, Map<DeliveryMethod, Integer> ranking, Set<Product> groupedProducts) {
        for (var entry : deliveryMethodWithProducts.entrySet()) {
            // Calculate how many products can still be delivered by this method after the last selection.
            int count = (int) entry.getValue().stream().filter(product -> !groupedProducts.contains(product)).count();
            ranking.put(entry.getKey(), count);
        }
    }



}
