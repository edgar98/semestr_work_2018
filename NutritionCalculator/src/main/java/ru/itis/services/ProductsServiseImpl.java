package ru.itis.services;

import ru.itis.forms.ProductForm;
import ru.itis.models.Product;
import ru.itis.repositories.CrudRepository;
import ru.itis.repositories.UsersRepository;

import java.util.List;

public class ProductsServiseImpl implements ProductsService {

    private CrudRepository<Product> productRepository;
    private UsersRepository usersRepository;

    public ProductsServiseImpl(CrudRepository<Product> productRepository, UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void newProduct(ProductForm form) {
        productRepository.save(Product.builder()
                .name(form.getName())
                .kal(form.getKal())
                .fat(form.getFat())
                .prot(form.getProt())
                .carbon(form.getCarbon())
                .water(form.getWater())
                .build());
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(0L);
    }

    @Override
    public Product find(Long id) {
        return productRepository.find(id);
    }
}
