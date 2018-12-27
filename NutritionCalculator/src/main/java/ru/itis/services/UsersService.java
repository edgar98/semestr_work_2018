package ru.itis.services;

import ru.itis.forms.AccountForm;
import ru.itis.forms.NutritionForm;
import ru.itis.forms.SignInForm;
import ru.itis.forms.SignUpForm;
import ru.itis.models.Account;
import ru.itis.models.Stat;

import java.util.List;


public interface UsersService {
    void signUp(SignUpForm form);

    String signIn(SignInForm form);

    boolean isExistByCookie(String cookieValue);

    void createAccount(AccountForm form);

    Account getUser(String cookie);

    void addNutrition(NutritionForm form);

    Stat getStat(Long id);

    List<Stat> getAllStats(Long id);
}
