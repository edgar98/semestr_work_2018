package ru.itis.repositories;

import ru.itis.models.Account;

public interface UsersRepository extends CrudRepository<Account> {
    Account findByName(String name);

    Account getUserByUUID(String uuid);

    Account getUser(String cookie);

}
