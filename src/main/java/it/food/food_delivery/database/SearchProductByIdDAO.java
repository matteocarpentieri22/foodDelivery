package it.food.food_delivery.database;

import it.food.food_delivery.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchProductByIdDAO extends AbstractDAO<Product> {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT name,id_restaurant,description,price,available,category,photo,photo_type FROM food_delivery.product WHERE id_restaurant= ? AND name = ?;";

    /**
     * The id of the sagra
     */
    private final int id_restaurant;

    /**
     * The name of the product
     */
    private final String name;

    /**
     *
     * Creates a new object for searching products by id.
     *
     * @param con the connection to the database
     * @param id_sagra the id of the sagra of the product
     * @param name name of the product
     * @throws NullPointerException The Id of the sagra cannot be null.
     */
    public SearchProductByIdDAO(final Connection con, final int id_restaurant, final String name) {
        super(con);

        if (name == null) {
            LOGGER.error("The category cannot be null.");
            throw new NullPointerException("the name of the product cannot be null");
        }

        this.id_restaurant = id_restaurant;
        this.name = name;
    }

    @Override
    public void doAccess() throws Exception {
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        //the returned product
        Product p=null;

        try{
            pstmt= con.prepareStatement(STATEMENT);
            pstmt.setInt(1,id_restaurant);
            pstmt.setString(2, name);

            rs=pstmt.executeQuery();

            if(rs.next()){
                p=new Product(rs.getString("name"),rs.getInt("id_restaurant"),
                        rs.getString("description"), rs.getDouble("price"),
                        rs.getBoolean("available"),
                        rs.getString("category"), rs.getBytes("photo"), rs.getString("photo_type"));
            }


            LOGGER.info("Product with id_restaurant %d and of name %s successfully retrieved", id_restaurant, name);
        }finally{
            if(rs!=null) rs.close();

            if(pstmt!=null) pstmt.close();
        }

        this.outputParam=p;
    }
}
