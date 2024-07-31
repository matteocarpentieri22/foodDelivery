package it.food.food_delivery.database;

import it.food.food_delivery.resource.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Insert a product into the table Product
 * @version 1.0
 * @since 1.0
 */
public final class InsertProductDAO extends AbstractDAO
{
    /**
     * The SQL statement to be executed for insert into Product table
     */
    private static final String STATEMENT = "INSERT INTO food_delivery.product (name,id_restaurant,description,price,available,category,photo,photo_type) VALUES (?,?,?,?,?,?,?,?)";

    /**
     * @param product Product Object to insert
     */
    private final Product product;


    /**
     * Constructor for the class.
     * @param con connection to the database.
     * @param product product that is inserted in the database.
     * @throws NullPointerException The product cannot be null.
     */
    public InsertProductDAO(final Connection con, final Product product)
    {
        super(con);

        if(product == null)
        {
            LOGGER.error("The product is not valid");
            throw new NullPointerException("The product is not valid");
        }

        this.product = product;
    }

    /**
     *
     * Product Insert execution.
     * @throws SQLException if some error occurred during query execution
     */
    @Override
    protected void doAccess() throws SQLException
    {
        PreparedStatement pstmt = null;
        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1,product.getName());
            pstmt.setInt(2,product.getIdRestaurant());
            pstmt.setString(3,product.getDescription());
            pstmt.setDouble(4,product.getPrice());
            pstmt.setBoolean(5,product.getAvailable());
            pstmt.setString(6,product.getCategory());
            pstmt.setBytes(7,product.getPhoto());
            pstmt.setString(8,product.getPhotoType());


            pstmt.execute();
            LOGGER.info("The product "+product.getName()+" has been insert correctly");
        }
        finally
        {
            if(pstmt != null)
                pstmt.close();
        }
    }
}
