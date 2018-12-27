package ru.itis.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.forms.AccountForm;
import ru.itis.forms.NutritionForm;
import ru.itis.forms.SignInForm;
import ru.itis.forms.SignUpForm;
import ru.itis.models.*;
import ru.itis.repositories.*;

import java.util.List;
import java.util.UUID;

public class UsersServiceImpl implements UsersService {

    private PasswordEncoder encoder;

    private UsersRepository usersRepository;
    private AuthRepository authRepository;
    private CrudRepository<Need> needsRepository;
    private CrudRepository<Product> prefsRepository;
    private CrudRepository<Nutrition> nutritionRepository;
    private CrudRepository<Stat> statRepository;
    private BasketService basketService;
    private BasketRepository basketRepository;

    public UsersServiceImpl(UsersRepository usersRepository,
                            AuthRepository authRepository,
                            BasketRepository basketRepository,
                            CrudRepository<Need> needsRepository,
                            CrudRepository<Product> prefsRepository,
                            CrudRepository<Nutrition> nutritionRepository,
                            CrudRepository<Stat> statRepository) {
        this.usersRepository = usersRepository;
        this.authRepository = authRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.basketRepository=basketRepository;
        this.needsRepository=needsRepository;
        this.prefsRepository=prefsRepository;
        this.nutritionRepository=nutritionRepository;
        this.statRepository=statRepository;
        this.basketService = new BasketServiceImpl(basketRepository);
    }

    @Override
    public void signUp(SignUpForm form) {
        Account user = Account.builder()
                .username(form.getName())
                .passwordHash(encoder.encode(form.getPassword()))
                .build();

        usersRepository.save(user);
    }

    @Override
    public void createAccount(AccountForm form){
        Account user = Account.builder()
                .id(form.getId())
                .firstname(form.getFirstname())
                .lastname(form.getLastname())
                .weight(form.getWeight())
                .height(form.getHeight())
                .age(form.getAge())
                .is_woman(form.getIs_woman())
                .activity(form.getActivity())
                .build();

        usersRepository.update(user);
        Need need = calculateNeeds(user);
        needsRepository.update(need);
    }

    public static Need calculateNeeds(Account user) {
        Float kal = (float)(665.1 + 9.6 * user.getWeight() + 1.85 * user.getHeight() - 4.68 * user.getAge()) * user.getActivity();
        Float fat = (float)(kal * 0.3) / 9;
        Float prot = (float)(user.getWeight() * 1.5);
        Float carbon = (float)(user.getWeight() * user.getActivity() * 2.625);
        Float water = user.getIs_woman() ? (float)(user.getWeight() * 31) : (float)(user.getWeight() * 35);
        return Need.builder()
                .user_id(user.getId())
                .kal(kal)
                .fat(fat)
                .prot(prot)
                .carbon(carbon)
                .water(water)
                .build();
    }

    @Override
    public String signIn(SignInForm form) {
        Account user = usersRepository.findByName(form.getName());

        if (user != null && encoder.matches(form.getPassword(), user.getPasswordHash())) {
            String cookieValue = UUID.randomUUID().toString();

            Auth auth = Auth.builder()
                    .user(user)
                    .cookieValue(cookieValue)
                    .build();

            authRepository.save(auth);

            return cookieValue;
        }
        return null;
    }

    @Override
    public boolean isExistByCookie(String cookieValue) {
        return authRepository.findByCookieValue(cookieValue) != null;
    }

    @Override
    public Account getUser(String cookie) {

        Account user = usersRepository.getUser(cookie);

        user.setPrefs(prefsRepository.findAll(user.getId()));

        user.setNutritions(nutritionRepository.findAll(user.getId()));

        return user;


    }

    @Override
    public void addNutrition(NutritionForm form) {
        nutritionRepository.save(Nutrition.builder()
                .user_id(form.getUser_id())
                .product(form.getProduct())
                .amount(form.getAmount())
                .date(form.getDate())
                .build());
        statRepository.update(Stat.builder()
                .user_id(form.getUser_id())
                .kal(form.getProduct().getKal() * form.getAmount())
                .fat(form.getProduct().getFat() * form.getAmount())
                .prot(form.getProduct().getProt() * form.getAmount())
                .carbon(form.getProduct().getCarbon() * form.getAmount())
                .water(form.getProduct().getWater() * form.getAmount())
                .date(form.getDate())
                .build());
    }

    @Override
    public Stat getStat(Long id) {
        return statRepository.find(id);
    }

    @Override
    public List<Stat> getAllStats(Long id) {
        return statRepository.findAll(id);
    }
}
