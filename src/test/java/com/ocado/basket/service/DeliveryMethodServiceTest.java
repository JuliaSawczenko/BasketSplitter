package com.ocado.basket.service;

import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.mocks.MockedValues;
import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryMethodServiceTest {

    private DeliveryMethodService deliveryMethodService;

    @BeforeEach
    void setUp() {
        deliveryMethodService = new DeliveryMethodService(MockedValues.PRODUCTS_WITH_DELIVERY_METHODS);
    }

    @Test
    void shouldReturnDeliveryMethods_WhenProductIsFound() throws InvalidProductException {
        // Given
        Product existingProduct = new Product("Fond - Chocolate");

        // When
        List<DeliveryMethod> deliveryMethods = deliveryMethodService.getDeliveryMethodsForProduct(existingProduct);

        // Then
        assertFalse(deliveryMethods.isEmpty(), "Delivery methods should not be empty for the existing product.");
        assertEquals("Pick-up point", deliveryMethods.get(0).name(), "First delivery method name should match: Pick-up point");
    }

    @Test
    void shouldThrowInvalidProductException_WhenProductIsNotFound() {
        // Given
        Product nonExistingProduct = new Product("Juice");

        //  Then
        InvalidProductException thrownException = assertThrows(InvalidProductException.class, () -> {
            deliveryMethodService.getDeliveryMethodsForProduct(nonExistingProduct);
        }, "An InvalidProductException should be thrown for a non-existing product");

        assertEquals("Product Juice is not found in the configuration file.", thrownException.getMessage(), "Exception message should match the expected one");
    }
}
