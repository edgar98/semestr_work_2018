package ru.itis.servlets;

import ru.itis.forms.AccountForm;
import ru.itis.models.Account;
import ru.itis.services.UsersService;
import ru.itis.services.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Account form = Account.builder()
                .weight(Float.parseFloat(request.getParameter("weight")))
                .height(Integer.parseInt(request.getParameter("height")))
                .age(Integer.parseInt(request.getParameter("age")))
                .activity(Float.parseFloat(request.getParameter("activity")))
                .is_woman(getSex(request.getParameter("sex")))
                .build();
        request.setAttribute("calc",  UsersServiceImpl.calculateNeeds(form));
        request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
    }

    private Boolean getSex(String sex) {
        return !sex.equals("male");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
    }
}
