package it.food.food_delivery.servlet;

import it.food.food_delivery.database.SearchCategoriesDAO;
import it.food.food_delivery.resource.*;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.StringFormattedMessage;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MenuServlet", urlPatterns = { "" })
public class MenuServlet extends AbstractDatabaseServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //List<Product> products;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        List<Category> categories;

        int restaurant = 1;
        try {
            SearchCategoriesDAO dao = new SearchCategoriesDAO(getConnection());
            dao.access();
            categories = dao.getOutputParam();
           
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h1>Invalid Restaurant ID</h1></body></html>");
            return;
        } 
        try {

            request.setAttribute("restaurant", 1);
            request.getRequestDispatcher("/jsp/menu.jsp").forward(request, response);

            } catch(Exception e) {
            LOGGER.error(new StringFormattedMessage("Unable to send response when searching sagra containing pattern [%s]"));
            throw e;
        } finally {
            out.close();
        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

