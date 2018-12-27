package ru.itis.services;

import ru.itis.models.Nutrition;
import ru.itis.models.Need;
import ru.itis.models.Account;

import java.util.List;


public interface BasketService {
    List<Need> addProductToUserBasket(Nutrition basket, Long productId);
    Nutrition createBasket(Account user);
    List<Need> getProduct(Long basketId);
    Nutrition getBasket(Account user);
}
