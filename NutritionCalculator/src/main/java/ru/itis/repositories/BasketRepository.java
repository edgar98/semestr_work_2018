package ru.itis.repositories;

import ru.itis.models.Nutrition;
import ru.itis.models.Need;
import ru.itis.models.Account;
import java.util.List;

public interface BasketRepository {
    void addProduct(Long product, Long basket);

    Nutrition createCookieBasket(Account user);

    List<Need> getProducts(Long basketId);

    /* Nutrition findByCookieValue(String cookieValue);*/
    Nutrition getBasket(Account user);

}
