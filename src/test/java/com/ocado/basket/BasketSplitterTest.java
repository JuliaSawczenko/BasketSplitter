package com.ocado.basket;

import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.mocks.MockedValues;
import com.ocado.basket.service.DeliveryOptimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketSplitterTest {

    @InjectMocks
    private BasketSplitter basketSplitter;

    @Mock
    private DeliveryOptimizer mockDeliveryOptimizer;

    @BeforeEach
    void setUp() {
        basketSplitter = new BasketSplitter(mockDeliveryOptimizer);
    }

    @Test
    void should_ReturnCorrectSplit_When_ValidItemsProvided() throws InvalidProductException {
        // Given
        List<String> items = MockedValues.PRODUCTS;
        Map<String, List<String>> expectedGroups = MockedValues.EXPECTED_GROUPS_STRING;

        // When
        when(mockDeliveryOptimizer.getDeliveryGroups(any())).thenReturn(MockedValues.EXPECTED_GROUPS);

        // Then
        Map<String, List<String>> actualGroups = basketSplitter.split(items);
        assertNotNull(actualGroups, "The result should not be null");
        assertEquals(expectedGroups, actualGroups, "The expected and actual groups should be identical");
    }

    @Test
    void should_ThrowInvalidProductException_When_ProductIsNotInConfiguration() throws InvalidProductException {
        // Given
        List<String> items = List.of("Cheese - St. Andre", "Invalid Product");

        // When
        when(mockDeliveryOptimizer.getDeliveryGroups(any())).thenThrow(new InvalidProductException("Product Invalid Product is not found in the configuration file"));

        // Then
        InvalidProductException thrownException = assertThrows(InvalidProductException.class, () -> basketSplitter.split(items),
                "Expected BasketSplitter to throw InvalidProductException for invalid products");

        assertEquals("Product Invalid Product is not found in the configuration file", thrownException.getMessage(), "Exception message should match the expected one");
    }

    @Test
    void should_ThrowIllegalArgumentException_When_ItemsListIsNull() {
        // Given
        List<String> items = null;

        // Then
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> {
            basketSplitter.split(items);
        }, "BasketSplitter should throw IllegalArgumentException when items list is null");

        assertEquals("The list of items cannot be null or empty.", thrownException.getMessage(), "Exception message should match the expected one");
    }

    @Test
    void should_ThrowIllegalArgumentException_When_ItemsListIsEmpty() {
        // Given
        List<String> items = Collections.emptyList();

        // Then
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> {
            basketSplitter.split(items);
        }, "BasketSplitter should throw IllegalArgumentException when items list is empty");

        assertEquals("The list of items cannot be null or empty.", thrownException.getMessage(), "Exception message should match the expected one");
    }
}

