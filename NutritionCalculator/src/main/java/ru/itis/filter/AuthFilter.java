package ru.itis.filter;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.models.Need;
import ru.itis.models.Nutrition;
import ru.itis.models.Product;
import ru.itis.models.Stat;
import ru.itis.repositories.*;
import ru.itis.services.BasketService;
import ru.itis.services.UsersService;
import ru.itis.services.UsersServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/account")
public class AuthFilter implements Filter {

    private UsersService usersService;
    private BasketService basketService;
    private BasketRepository basketRepository;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("edgar");
        dataSource.setPassword("qwerty007");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/nutrition");
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        AuthRepository authRepository = new AuthRepositoryImpl(dataSource);
        CrudRepository<Need> needsRepository = new NeedsRepositoryImpl(dataSource);
        CrudRepository<Product> prefsRepository = new PrefsRepositoryImpl(dataSource);
        CrudRepository<Nutrition> nutritionRepository = new NutritionsRepositoryImpl(dataSource);
        CrudRepository<Stat> statRepository = new StatsRepository(dataSource);
        usersService = new UsersServiceImpl(usersRepository, authRepository, basketRepository,
                needsRepository, prefsRepository, nutritionRepository, statRepository);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Cookie cookies[] = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth")) {
                    if (usersService.isExistByCookie(cookie.getValue())) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            response.sendRedirect("/signIn");
            return;
        }
        response.sendRedirect("/signIn");
        return;
    }

    @Override
    public void destroy() {

    }
}
