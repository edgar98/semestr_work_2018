package ru.itis.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.forms.SignUpForm;
import ru.itis.models.Need;
import ru.itis.models.Nutrition;
import ru.itis.models.Product;
import ru.itis.models.Stat;
import ru.itis.repositories.*;
import ru.itis.services.BasketService;
import ru.itis.services.BasketServiceImpl;
import ru.itis.services.UsersService;
import ru.itis.services.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private UsersService usersService;
    private BasketService basketService;
    private BasketRepository basketRepository;

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
        basketService = new BasketServiceImpl(basketRepository);
        CrudRepository<Need> needsRepository = new NeedsRepositoryImpl(dataSource);
        CrudRepository<Product> prefsRepository = new PrefsRepositoryImpl(dataSource);
        CrudRepository<Nutrition> nutritionRepository = new NutritionsRepositoryImpl(dataSource);
        CrudRepository<Stat> statRepository = new StatsRepository(dataSource);
        usersService = new UsersServiceImpl(usersRepository, authRepository, basketRepository,
                needsRepository, prefsRepository, nutritionRepository, statRepository);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignUpForm form = SignUpForm.builder()
                .name(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();

        usersService.signUp(form);

        response.sendRedirect("/signIn");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }
}
