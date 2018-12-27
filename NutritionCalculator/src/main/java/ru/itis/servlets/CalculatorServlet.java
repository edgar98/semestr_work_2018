package ru.itis.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.forms.AccountForm;
import ru.itis.forms.NutritionForm;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet { 
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
        CrudRepository<Product> productRepository = new ProductsRepositoryImpl(dataSource);
        productsService = new ProductsServiseImpl(productRepository, usersRepository);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                Account user = usersService.getUser(cookie.getValue());
                Long id = user.getId();
                NutritionForm form = NutritionForm.builder()
                        .user_id(id)
                        .product(productsService.find(Long.parseLong(request.getParameter("product"))))
                        .amount(Float.parseFloat(request.getParameter("amount")))
                        .date(LocalDate.now())
                        .build();
                usersService.addNutrition(form);
                response.sendRedirect("/calculator");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                Account user = usersService.getUser(cookie.getValue());
                Stat stat = usersService.getStat(user.getId());
                List<Product> products = productsService.getAllProducts();
                request.setAttribute("user", user);
                request.setAttribute("today_nutrition", stat);
                request.setAttribute("products", products);
                request.getRequestDispatcher("jsp/calculator.jsp").forward(request, response);
                break;
            }
        }
        response.sendRedirect("/signIn");
    }
}