package it.food.food_delivery.database;


import it.food.food_delivery.resource.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Loads the photo of a Product.
 *
 * @author Simone Merlo (simone.merlo@studenti.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class LoadProductPhotoDAO extends AbstractDAO<Product> {

    /**
     * The SQL statement to be executed.
     */
    private static final String STATEMENT = "SELECT photo, photo_type FROM food_delivery.product WHERE id_restaurant=? AND name=?";

    /**
     * The id of the sagra.
     */
    private final int id_restaurant;

    /**
     * The name of the product.
     */
    private final String name;


    /**
     * Creates a new object for loading products photos.
     *
     * @param con   the connection to the database.
     * @param id_sagra the id of the sagra to which the product belongs to.
     * @param name the name of the product.
     */
    public LoadProductPhotoDAO(Connection con, int id_restaurant, String name) {
        super(con);
        this.id_restaurant = id_restaurant;
        this.name = name;
    }


    @Override
    public void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        Product p=null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id_restaurant);
            pstmt.setString(2, name);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                p = new Product(null, -1, null, Double.MIN_VALUE, false,
                        false, null, rs.getBytes("photo"), rs.getString("photo_type"));

                LOGGER.info("Photo for product %s successfully loaded.", name);
            } else {
                LOGGER.warn("Product %s for sagra %d not found.", name,id_sagra);
                throw new SQLException(String.format("Product %s for sagra %d not found.", name,id_restaurant), "NOT_FOUND");
            }


        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        this.outputParam = p;
    }
}

