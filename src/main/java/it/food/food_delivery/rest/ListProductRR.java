package 
it.food.food_delivery.rest;

import it.food.food_delivery.database.SearchAllAvailableProductByRestaurantDAO;
import it.food.food_delivery.database.SearchCategoriesDAO;
import it.food.food_delivery.database.SearchCategoriesWithAvailableProductsDAO;
import it.food.food_delivery.database.SearchProductByCategoryDAO;
import it.food.food_delivery.resource.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListProductRR extends AbstractRR{


    /**
     * Creates a new REST resource for listing {@code Product}s for the given sagra.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public ListProductRR(final HttpServletRequest req, HttpServletResponse res, Connection con){
        super(Actions.LIST_PRODUCT, req,res,con);
    }


    @Override
    protected void doServe() throws IOException {
        List<Product> pl=null;
        Message m=null;
        int id_restaurant=-1;




        try {

            //retrieving the id of the sagra from the URI
            String path= req.getRequestURI();
            path=path.substring(path.lastIndexOf("all") +3);

            id_restaurant=1;


            //retrieving the products
            pl=new SearchAllAvailableProductByRestaurantDAO(con,id_restaurant).access().getOutputParam();


            if (pl != null) {
                LOGGER.info("Product(s) successfully listed.");

                res.setStatus(HttpServletResponse.SC_OK);
                List<Product> productsWithPhotoPath=new ArrayList<>();
                for(Product p: pl){
                    if(p.hasPhoto()){
                        String photoPath= String.format("%s://%s:%d%s/load-product-photo/?name=%s&sagra=%d",req.getScheme(),req.getServerName(),req.getServerPort(),req.getContextPath(),p.getName().replaceAll(" ","+"),p.getIdRestaurant());
                        productsWithPhotoPath.add(new Product(p.getName(),p.getIdRestaurant(),p.getDescription(),p.getPrice(),p.getAvailable(),p.getCategory(),p.getPhoto(),p.getPhotoType(),photoPath));
                    }else{
                        productsWithPhotoPath.add(p);
                    }
                }
                new ResourceList<Product>(productsWithPhotoPath).toJSON(res.getOutputStream());
            } else { // it should not happen
                LOGGER.error("Fatal error while listing products(s).");

                m = new Message("Cannot list product(s): unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot list products(s): unexpected database error.", ex);

            m = new Message("Cannot list products(s): unexpected database error.", "E5A1", ex.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        } catch(IndexOutOfBoundsException | NullPointerException | NumberFormatException ex) {
            LOGGER.warn("Cannot list the products: wrong format for URI /product/all/{sagra}.", ex);

            m = new Message("Cannot list the products: wrong format for URI /product/all/{sagra}.", "E4A7",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        }
    }
}
