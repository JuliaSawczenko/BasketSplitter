package com.ocado.basket;

import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.mocks.MockedValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private BasketSplitter basketSplitter;

    @BeforeEach
    void setUp() throws Exception {
        String pathToConfig = Paths.get(getClass().getResource("/validConfig.json").toURI()).toString();
        basketSplitter = new BasketSplitter(pathToConfig);
    }

    @Test
    void shouldCorrectlySplitItemsBasedOnConfig() throws Exception {
        // Given
        List<String> items = MockedValues.PRODUCTS;
        Map<String, List<String>> expectedGroups = MockedValues.EXPECTED_GROUPS_STRING;

        // When
        Map<String, List<String>> splitResult = basketSplitter.split(items);

        // Then
        assertNotNull(splitResult);
        assertTrue(splitResult.containsKey("Pick-up point"));
        assertTrue(splitResult.get("Pick-up point").containsAll(items));
        assertEquals(expectedGroups, splitResult, "The expected and actual groups should be identical");
    }


    @Test
    void shouldHandleProductNotInConfig() {
        // Given
        List<String> items = List.of("Nonexistent Product");

        // Then
        assertThrows(InvalidProductException.class, () -> basketSplitter.split(items));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForEmptyInput() {
        // Given
        List<String> items = List.of();

        // Then
        assertThrows(IllegalArgumentException.class, () -> basketSplitter.split(items));
    }
}

