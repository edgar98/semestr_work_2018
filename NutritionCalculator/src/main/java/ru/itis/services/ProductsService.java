package ru.itis.services;

import ru.itis.forms.ProductForm;
import ru.itis.models.Product;

import java.util.List;

public interface ProductsService {
    void newProduct(ProductForm form);
    List<Product> getAllProducts();
    Product find(Long id);
}
