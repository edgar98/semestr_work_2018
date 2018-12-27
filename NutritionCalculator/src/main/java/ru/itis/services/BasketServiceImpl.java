package ru.itis.services;

import ru.itis.models.Nutrition;
import ru.itis.models.Need;
import ru.itis.models.Account;
import ru.itis.repositories.BasketRepository;
import ru.itis.repositories.UsersRepositoryJdbcTemplateImpl;

import java.util.List;

public class BasketServiceImpl implements BasketService {


    private BasketRepository basketRepository;
    private UsersRepositoryJdbcTemplateImpl userRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository=basketRepository;
    }

    @Override
    public List<Need> getProduct(Long basketId) {
        return basketRepository.getProducts(basketId);
    }

    @Override
    public Nutrition createBasket(Account user) {
        return basketRepository.createCookieBasket(user);
    }

    @Override
    public List<Need> addProductToUserBasket(Nutrition basket, Long productId) {
        basketRepository.addProduct(productId, basket.getId());
        return basketRepository.getProducts(basket.getId());
    }

    @Override
    public Nutrition getBasket(Account user) {
        return basketRepository.getBasket(user);
    }


}
