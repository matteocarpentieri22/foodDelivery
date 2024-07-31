package it.food.food_delivery.servlet;

import it.food.food_delivery.database.SearchAllAvailableProductByRestaurantDAO;
import it.food.food_delivery.resource.Product;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MenuServlet", urlPatterns = { "" })
public class MenuServlet extends AbstractDatabaseServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        List<Product> products;
        

        try {
            SearchAllAvailableProductByRestaurantDAO dao = new SearchAllAvailableProductByRestaurantDAO(getConnection(),1);
            dao.access();
            products = dao.getOutputParam();
            out.println("<html>");
            out.println("<head><title>Available Products</title></head>");
            out.println("<body>");
            out.println("<h1>Available Products</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Price</th></tr>");

            for (Product product : products) {
                out.println("<tr>");
                out.println("<td>" + product.getName() + "</td>");
                out.println("<td>" + product.getPrice() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h1>Error retrieving products</h1></body></html>");
        } finally {
            out.close();
        }
    }
}

