package com.ocado.basket.service;

import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.mocks.MockedValues;
import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryOptimizerTest {

    @InjectMocks
    private DeliveryOptimizer deliveryOptimizer;

    @Mock
    private DeliveryMethodService deliveryMethodService;

    @BeforeEach
    void setUp() throws InvalidProductException {
        when(deliveryMethodService.getDeliveryMethodsForProduct(any(Product.class)))
                .thenAnswer(request -> {
                    Product product = request.getArgument(0, Product.class);
                    return MockedValues.PRODUCTS_WITH_DELIVERY_METHODS.entrySet().stream()
                            .filter(entry -> entry.getKey().name().equals(product.name()))
                            .findFirst()
                            .map(Map.Entry::getValue)
                            .orElseThrow(() -> new InvalidProductException("Product " + product.name() + " is not found in the configuration file."));
                });
    }


    @Test
    void shouldCorrectlyGroupProductsByDeliveryMethod() throws InvalidProductException {
        // Given
        List<String> productNames = MockedValues.PRODUCTS;

        // When
        Map<DeliveryMethod, List<Product>> groupedProducts = deliveryOptimizer.getDeliveryGroups(productNames);

        // Then
        assertEquals(MockedValues.EXPECTED_GROUPS.size(), groupedProducts.size(), "The size should match the expected size of the groups.");
    }
}
