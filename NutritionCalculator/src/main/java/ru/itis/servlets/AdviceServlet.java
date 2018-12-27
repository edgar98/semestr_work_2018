package ru.itis.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.models.*;
import ru.itis.repositories.*;
import ru.itis.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/advice")
public class AdviceServlet extends HttpServlet {
    private BasketRepository basketRepository;
    private UsersService usersService;
    private ProductsService productsService;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("edgar");
        dataSource.setPassword("qwerty007");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/nutrition");
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        AuthRepository authRepository = new AuthRepositoryImpl(dataSource);
        basketRepository = new BasketRepositoryImpl(dataSource);
        BasketService basketService = new BasketServiceImpl(basketRepository);
        CrudRepository<Need> needsRepository = new NeedsRepositoryImpl(dataSource);
        CrudRepository<Product> prefsRepository = new PrefsRepositoryImpl(dataSource);
        CrudRepository<Nutrition> nutritionRepository = new NutritionsRepositoryImpl(dataSource);
        CrudRepository<Stat> statRepository = new StatsRepository(dataSource);
        usersService = new UsersServiceImpl(usersRepository, authRepository, basketRepository,
                needsRepository, prefsRepository, nutritionRepository, statRepository);
        CrudRepository<Product> productRepository = new PrefsRepositoryImpl(dataSource);
        productsService = new ProductsServiseImpl(productRepository, usersRepository);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                Account user = usersService.getUser(cookie.getValue());
                request.setAttribute("user", user);
                // get user stats for today
                request.setAttribute("today_nutrition", user.getNutritions().stream()
                        .filter((nutrition -> nutrition.getDate().equals(LocalDate.now()))).collect(Collectors.toList()));
                // send advice to page
                request.setAttribute("advice", calculateAdvice(user, productsService.getAllProducts()));

                request.getRequestDispatcher("jsp/calculator.jsp").forward(request, response);
                break;
            }
        }




        request.getRequestDispatcher("jsp/advice.jsp").forward(request, response);

    }

    private Advice calculateAdvice(Account user, List<Product> allProducts) {
        Stat stat = usersService.getStat(user.getId());
        Float kalDiff = user.getNeeds().getKal() - stat.getKal();
        Float fatDiff = user.getNeeds().getFat() - stat.getFat();
        Float protDiff = user.getNeeds().getProt() - stat.getProt();
        Float carbonDiff = user.getNeeds().getCarbon() - stat.getCarbon();
        Float waterDiff = user.getNeeds().getWater() - stat.getWater();
        // Считаем процент недостатка, находим наибольший,
        // пытаемся его добить продуктом с максимальным содержанием этого параметра
        // Находим следующее недоставание, добиваем его (возможно удаляя предыдущий продукт, если вылетаем
        // по предыдущщему параметру
        // выводим пользователю список того, что ему можно сожрать
        // TODO: calculate advice according user needs
        return null;
    }
}
