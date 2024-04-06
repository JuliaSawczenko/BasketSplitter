package com.ocado.basket.service;

import com.ocado.basket.exception.ConfigLoaderException;
import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigLoaderTest {

    @Test
    void should_LoadConfigurationCorrectly_When_FileIsCorrect() throws URISyntaxException, ConfigLoaderException {
        // Given
        String validPath = Paths.get(getClass().getResource("/validConfig.json").toURI()).toString();

        // When
        Map<Product, List<DeliveryMethod>> config = ConfigLoader.loadConfig(validPath);

        // Then
        assertNotNull(config, "Configuration file should not be null");
        assertFalse(config.isEmpty(), "Configuration file should not be empty");

        Product specificProduct = new Product("Cheese Cloth");
        assertTrue(config.containsKey(specificProduct), "Configuration should contain Cheese Cloth");

        List<DeliveryMethod> deliveryMethods = config.get(specificProduct);
        assertNotNull(deliveryMethods, "Delivery methods for Cheese Cloth should not be null");
        assertEquals(5, deliveryMethods.size(), "Cheese Cloth should have exactly 5 delivery methods");

        assertTrue(deliveryMethods.contains(new DeliveryMethod("Courier")), "Cheese Cloth should have Courier method");
        assertTrue(deliveryMethods.contains(new DeliveryMethod("Parcel locker")), "Cheese Cloth should have Parcel locker method");
    }

    @Test
    void should_ThrowConfigLoaderException_When_FileDoesNotExist() {
        String nonExistentPath = "path/nonexistent/config.json";
        assertThrows(ConfigLoaderException.class, () -> ConfigLoader.loadConfig(nonExistentPath));
    }

    @Test
    void should_ThrowConfigLoaderException_When_FileHasWrongStructure() throws URISyntaxException {
        String wrongFilePath = Paths.get(getClass().getResource("/wrongConfig.json").toURI()).toString();
        assertThrows(ConfigLoaderException.class, () -> ConfigLoader.loadConfig(wrongFilePath));
    }
}
