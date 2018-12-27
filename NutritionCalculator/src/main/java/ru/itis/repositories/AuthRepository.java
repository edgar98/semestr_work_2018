package ru.itis.repositories;

import ru.itis.models.Auth;


public interface AuthRepository extends CrudRepository<Auth> {
    Auth findByCookieValue(String cookieValue);
}
