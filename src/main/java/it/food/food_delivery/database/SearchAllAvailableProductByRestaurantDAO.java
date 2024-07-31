package it.food.food_delivery.database;


import it.food.food_delivery.resource.Category;
import it.food.food_delivery.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches the products given its id (composed by the identifier of the restaurant and name).
 *
 * @author Riccardo Fazzi
 * @version 1.00
 * @since 1.00
 */
public class SearchAllAvailableProductByRestaurantDAO extends AbstractDAO<List<Product>>{
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT name,id_restaurant,description,price,available,category,photo,photo_type FROM food_delivery.product WHERE id_restaurant= ? AND available=TRUE;";

    /**
     * The id of the sagra
     */
    private final int id_restaurant;


    /**
     * Creates a new object for searching available products of a restaurant.
     *
     * @param con    the connection to the database.
     * @param id_restaurant the id of the restaurant.

     */
    public SearchAllAvailableProductByRestaurantDAO(final Connection con, final int id_restaurant) {
        super(con);

        this.id_restaurant = id_restaurant;
    }

    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        //results of the search
        final List<Product> products= new ArrayList<>();

        try{
            pstmt= con.prepareStatement(STATEMENT);
            pstmt.setInt(1,id_restaurant);

            rs=pstmt.executeQuery();

            while(rs.next()){
                products.add(new Product(rs.getString("name"),rs.getInt("id_restaurant"),
                        rs.getString("description"), rs.getDouble("price"), rs.getBoolean("available"),
                        rs.getString("category"), rs.getBytes("photo"), rs.getString("photo_type")));
            }

            LOGGER.info("Product(s) with id_restaurant %d ", id_restaurant);

        }finally{
            if(rs!=null) rs.close();

            if(pstmt!=null) pstmt.close();
        }

        this.outputParam=products;
    }
}
