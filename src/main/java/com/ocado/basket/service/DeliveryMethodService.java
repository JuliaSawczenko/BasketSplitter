package com.ocado.basket.service;

import com.ocado.basket.exception.InvalidProductException;
import com.ocado.basket.model.DeliveryMethod;
import com.ocado.basket.model.Product;

import java.util.List;
import java.util.Map;

public class DeliveryMethodService {

    private final Map<Product, List<DeliveryMethod>> productsWithDeliveryMethods;

    public DeliveryMethodService(Map<Product, List<DeliveryMethod>> productsWithDeliveryMethods) {
        this.productsWithDeliveryMethods = productsWithDeliveryMethods;
    }

    public List<DeliveryMethod> getDeliveryMethodsForProduct(Product product) {

        if (!productsWithDeliveryMethods.containsKey(product)) {
            throw new InvalidProductException("Product " + product.name() + " is not found in the configuration file.");
        }

        return productsWithDeliveryMethods.get(product);
    }
}
