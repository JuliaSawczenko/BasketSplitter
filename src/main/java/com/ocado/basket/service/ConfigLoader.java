package com.ocado.basket.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.exception.ConfigLoaderException;
import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigLoader {

    public static Map<Product, List<DeliveryMethod>> loadConfig(String absolutePathToConfigFile) throws ConfigLoaderException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> rawConfig;

        try {
            File file = Paths.get(absolutePathToConfigFile).toFile();
            rawConfig = objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new ConfigLoaderException("Failed to load configuration from file: " + absolutePathToConfigFile);
        }

        Map<Product, List<DeliveryMethod>> config = new HashMap<>();

        for (var entry : rawConfig.entrySet()) {
            Product product = new Product(entry.getKey());

            List<DeliveryMethod> deliveryMethods = entry.getValue().stream()
                    .map(DeliveryMethod::new)
                    .collect(Collectors.toList());

            config.put(product, deliveryMethods);
        }

        return config;
    }
}
