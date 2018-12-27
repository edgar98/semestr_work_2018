package ru.itis.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.forms.ProductForm;
import ru.itis.models.Product;
import ru.itis.repositories.*;
import ru.itis.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductsService productsService;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("edgar");
        dataSource.setPassword("qwerty007");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/nutrition");
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        CrudRepository<Product> productRepository = new ProductsRepositoryImpl(dataSource);
        productsService = new ProductsServiseImpl(productRepository, usersRepository);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProductForm form = ProductForm.builder()
                .name(request.getParameter("name"))
                .kal(Float.parseFloat(request.getParameter("kal")))
                .fat(Float.parseFloat(request.getParameter("fat")))
                .prot(Float.parseFloat(request.getParameter("prot")))
                .carbon(Float.parseFloat(request.getParameter("carbon")))
                .water(Float.parseFloat(request.getParameter("water")))
                .build();
        productsService.newProduct(form);
        response.sendRedirect("/calculator");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
