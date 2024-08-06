package it.food.food_delivery.rest;


import it.food.food_delivery.database.SearchCategoriesDAO;
import it.food.food_delivery.resource.Actions;
import it.food.food_delivery.resource.Category;
import it.food.food_delivery.resource.Message;
import it.food.food_delivery.resource.ResourceList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListCategoriesRR extends AbstractRR{

    /**
     * Creates a new REST resource for listing {@code Category}(ies).
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public ListCategoriesRR(final HttpServletRequest req, HttpServletResponse res, Connection con){
        super(Actions.LIST_CATEGORIES, req,res,con);
    }


    /**
     * Performs the actual logic needed to list the categories.
     *
     *
     * @throws IOException if any error occurs in the client/server communication.
     */
    @Override
    protected void doServe() throws IOException {
        Message m=null;
        List<Category> categories=null;
        try {

            // creates a new DAO for accessing the database and lists the products
            categories = new SearchCategoriesDAO(con).access().getOutputParam();

            if (categories != null) {
                LOGGER.info("Category(ies) successfully listed.");

                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(categories).toJSON(res.getOutputStream());
            } else { // it should not happen
                LOGGER.error("Fatal error while listing category(ies).");
                m = new Message("Cannot list category(ies): unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (SQLException ex) {
             LOGGER.error("Cannot list category(ies): unexpected database error.", ex);

             m = new Message("Cannot list category(ies): unexpected database error.", "E5A1", ex.getMessage()); res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             m.toJSON(res.getOutputStream());
    }
    }
}
